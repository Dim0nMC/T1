package org.example.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.protocol.types.Field;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.annotation.Metric;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class MetricAspect {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public MetricAspect(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Value("${spring.application.name}")
    private String serviceName;

    @Value("${metric.threshold-ms}")
    private long thresholdMs;



    @Around("@annotation(metric)")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint, Metric metric) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long duration = System.currentTimeMillis() - start;

        if (duration > thresholdMs) {
            try {
                String methodSignature = joinPoint.getSignature().toShortString();

                Map<String, Object> message = new HashMap<>();
                message.put("timestamp", LocalDateTime.now().toString());
                message.put("methodSignature", methodSignature);
                message.put("executionTimeMs", duration);

                Object[] args = joinPoint.getArgs();
                if (args.length > 0) message.put("args", args);

                String jsonMessage = objectMapper.writeValueAsString(message);
                kafkaTemplate.send(MessageBuilder
                        .withPayload(jsonMessage)
                        .setHeader(KafkaHeaders.TOPIC, "service_logs")
                        .setHeader(KafkaHeaders.KEY, serviceName)
                        .setHeader("type","WARNING")
                        .build());
            } catch (Exception ex) {
                System.err.println("Failed to log metric: " + ex.getMessage());
                ex.printStackTrace();
            }
        }

        return result;
    }
}
