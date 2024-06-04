package com.mariamkatamashvili.gym.messaging;

import com.mariamkatamashvili.gym.dto.WorkloadDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageProducer {
    @Value("${messaging.jms.destination}")
    private String destination;
    private final JmsTemplate jmsTemplate;

    public void sendMessage(WorkloadDTO workload) {
        String transactionId = MDC.get("X-Transaction-Id");
        String token = extractToken();

        jmsTemplate.convertAndSend(destination, workload, message -> {
            message.setStringProperty("_type", "WorkloadDTO");

            if (transactionId != null) {
                message.setStringProperty("X-Transaction-Id", transactionId);
            }
            if (token != null) {
                message.setStringProperty("Authorization", "Bearer " + token);
            }

            return message;
        });
    }

    private String extractToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getCredentials() instanceof String string) {
            return string;
        }
        return null;
    }
}