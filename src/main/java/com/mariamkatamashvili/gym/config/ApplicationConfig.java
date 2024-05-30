package com.mariamkatamashvili.gym.config;

import com.mariamkatamashvili.gym.config.logging.TransactionIdInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig implements WebMvcConfigurer {
    private final TransactionIdInterceptor transactionIdInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(transactionIdInterceptor);
    }
}