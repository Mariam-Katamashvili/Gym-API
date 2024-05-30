package com.mariamkatamashvili.gym.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import org.springframework.data.annotation.TypeAlias;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkloadDTO {
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Lastname is required")
    private String lastName;
    @NotNull(message = "Status is required")
    private Boolean isActive;
    @NotNull(message = "Date is required")
    private LocalDate date;
    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be positive")
    private Integer duration;
    @NotNull(message = "Action type is required")
    private ActionType actionType;
}