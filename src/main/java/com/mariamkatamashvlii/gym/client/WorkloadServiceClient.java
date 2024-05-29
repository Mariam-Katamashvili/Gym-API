package com.mariamkatamashvlii.gym.client;

import com.mariamkatamashvlii.gym.config.feign.FeignConfig;
import com.mariamkatamashvlii.gym.dto.WorkloadDTO;
import com.mariamkatamashvlii.gym.exception.GymException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "${svc.workload-svc.name}", configuration = FeignConfig.class)
@Component
public interface WorkloadServiceClient {
    @PostMapping("${svc.workload-svc.update-session-endpoint}")
    @CircuitBreaker(name = "trainerWorkService", fallbackMethod = "fallbackSendWorkload")
    void sendWorkload(@RequestBody WorkloadDTO workload);

    default void fallbackSendWorkload(WorkloadDTO workload, Throwable t) {
        if (t instanceof GymException gymException) {
            throw gymException;
        } else if (t instanceof CallNotPermittedException) {
            throw new GymException("CircuitBreaker 'WorkloadServiceClient' does not permit further calls. Service unavailable.");
        } else {
            throw new GymException("Unknown exception: unable to perform training session modification.");
        }
    }
}