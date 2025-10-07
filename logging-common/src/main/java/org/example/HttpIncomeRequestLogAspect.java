package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.protocol.types.Field;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.example.annotation.HttpIncomeRequestLog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Aspect
@Component
public class HttpIncomeRequestLogAspect {

    private final KafkaTemplate<String,String> kafkaTemplate;
    ObjectMapper objectMapper = new ObjectMapper();

    public HttpIncomeRequestLogAspect(KafkaTemplate<String,String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Value("${spring.application.name}")
    private String serviceName;

    @Before("@annotation(httpIncomeRequestLog)")
    public void logBefore(JoinPoint joinPoint, HttpIncomeRequestLog httpIncomeRequestLog) throws Throwable {

        String methodSignature = joinPoint.getSignature().toShortString();

        Map<String,Object> message = Map.of(
                "timestamp", LocalDateTime.now(),
                "method", methodSignature,
                "uri", joinPoint.getArgs()[0],
                "params", joinPoint.getArgs()[1],
                "body", joinPoint.getArgs()[2]
        );

        String jsonMessage = objectMapper.writeValueAsString(message);

        kafkaTemplate.send(MessageBuilder
                .withPayload(jsonMessage)
                .setHeader(KafkaHeaders.TOPIC, "service_logs")
                .setHeader(KafkaHeaders.KEY, serviceName)
                .setHeader("type", "INFO")
                .build());

    }
}
