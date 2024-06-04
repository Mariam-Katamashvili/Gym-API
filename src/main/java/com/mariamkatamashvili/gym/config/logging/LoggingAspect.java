package com.mariamkatamashvili.gym.config.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restController() {
    }

    @AfterReturning(pointcut = "restController()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String transactionId = MDC.get("X-Transaction-Id");
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        String responseToLog = result != null ? result.toString()
                .replaceAll("password=[^,]+", "password=***")
                .replaceAll("token=[^,]+", "token=***") : "null";

        log.info("Transaction ID: {}, Endpoint: {}.{}(), Response: {}",
                transactionId,
                className, methodName, responseToLog);
    }

    @AfterThrowing(pointcut = "restController()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        String transactionId = MDC.get("X-Transaction-Id");
        log.error("Transaction ID: {}, Endpoint: {}, Error: {}", transactionId, joinPoint.getSignature().toShortString(), e.getMessage());
    }
}