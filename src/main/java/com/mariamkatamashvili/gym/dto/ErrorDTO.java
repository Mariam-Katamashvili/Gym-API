package com.mariamkatamashvili.gym.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class ErrorDTO {
    @NonNull
    private String errorMessage;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> details = new HashMap<>();
    @NonNull
    private String errorCode;
}