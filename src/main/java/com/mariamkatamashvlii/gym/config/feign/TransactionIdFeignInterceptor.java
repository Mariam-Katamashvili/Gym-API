package com.mariamkatamashvlii.gym.config.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class TransactionIdFeignInterceptor implements RequestInterceptor {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();

            String transactionId = (String) request.getAttribute("X-Transaction-Id");
            if (transactionId != null) {
                requestTemplate.header("X-Transaction-Id", transactionId);
            }

            String token = request.getHeader(AUTHORIZATION_HEADER);
            if (token != null) {
                requestTemplate.header(AUTHORIZATION_HEADER, token);
            }
        } else {
            String token = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
            if (token != null) {
                requestTemplate.header(AUTHORIZATION_HEADER, "Bearer " + token);
            }
        }
    }
}