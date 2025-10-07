package org.example.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.example.annotation.HttpOutcomeRequestLog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Aspect
@Component
public class HttpOutcomeRequestLogAspect {

    private final KafkaTemplate<String, String> kafkaTemplate;
    ObjectMapper objectMapper = new ObjectMapper();

    public HttpOutcomeRequestLogAspect(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Value("${spring.application.name}")
    private String serviceName;

    @AfterReturning(pointcut = "@annotation(httpOutcomeRequestLog)",returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, HttpOutcomeRequestLog httpOutcomeRequestLog, Object result) throws Throwable {

        try{
            String methodSignature = joinPoint.getSignature().toShortString();

            Map<String,Object> message = Map.of(
                    "timestamp", LocalDateTime.now(),
                    "method",methodSignature,
                    "uri", joinPoint.getArgs()[0],
                    "params", joinPoint.getArgs()[1],
                    "body", joinPoint.getArgs()[2]
            );

            String jsonMessage = objectMapper.writeValueAsString(message);
            kafkaTemplate.send(MessageBuilder
                    .withPayload(jsonMessage)
                    .setHeader(KafkaHeaders.TOPIC, "service_logs")
                    .setHeader(KafkaHeaders.KEY, serviceName)
                    .setHeader("type","INFO")
                    .build());

            } catch (Exception ex) {
                System.err.println("Failed to log HTTP request: " + ex.getMessage());
                ex.printStackTrace();
            }
    }
}
