package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.annotation.LogDatasourceError;
import org.example.model.ErrorLog;
import org.example.repository.ErrorLogRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Aspect
@Component

public class LogDatasourceErrorAspect {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ErrorLogRepository errorLogRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public LogDatasourceErrorAspect(KafkaTemplate<String, String> kafkaTemplate,
                                    ErrorLogRepository errorLogRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.errorLogRepository = errorLogRepository;
    }

    @Value("${spring.application.name}")
    private String serviceName;

    @Around("@annotation(logDatasourceError)")
    public Object handleDatasourceErrors(ProceedingJoinPoint joinPoint,
                                         LogDatasourceError logDatasourceError) throws Throwable {
        try {
            return joinPoint.proceed(); //выполняем целевой метод
        } catch (Exception ex) {
            logError(joinPoint, ex, logDatasourceError.level());
            throw ex; //пробрасываем, если нужно обработать выше
        }
    }

    private void logError(ProceedingJoinPoint joinPoint, Exception ex, String level) throws Throwable {
        LocalDateTime timestamp = LocalDateTime.now();
        String methodSignature = joinPoint.getSignature().toShortString();
        String methodArgs = Arrays.toString(joinPoint.getArgs());

        String stackTrace = Arrays.stream(ex.getStackTrace())
                .map(StackTraceElement::toString)
                .collect(Collectors.joining("\n"));

        Map<String, Object> message = Map.of(
                "timestamp", timestamp.toString(),
                "method", methodSignature,
                "error", ex.getMessage(),
                "stackTrace", stackTrace,
                "args", methodArgs
        );
        String jsonMessage = objectMapper.writeValueAsString(message);
        try {
            kafkaTemplate.send(MessageBuilder
                    .withPayload(jsonMessage)
                    .setHeader(KafkaHeaders.TOPIC, "service_logs")
                    .setHeader(KafkaHeaders.KEY , serviceName)
                    .setHeader("type", level)
                    .setHeader("value", level)
                    .build());
        } catch (Exception kafkaEx) {
            ErrorLog errorLog = new ErrorLog();
            errorLog.setTimestamp(timestamp);
            errorLog.setMethodSignature(methodSignature);
            errorLog.setExceptionMessage(ex.getMessage());
            errorLog.setStackTrace(stackTrace);
            errorLog.setMethodArgs(methodArgs);
            errorLogRepository.save(errorLog);
        }

        System.err.printf("[%s] %s: %s%n", level, methodSignature);
        ex.printStackTrace();
    }
}
