package com.mariamkatamashvili.gym.dto.traineeDto;

import com.mariamkatamashvili.gym.dto.trainerDto.TrainerDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateResponseDTO {
    @Valid
    private String username;

    @Valid
    private String firstName;

    @Valid
    private String lastName;

    @Valid
    private LocalDate birthday;

    @Valid
    private String address;

    @Valid
    private Boolean isActive;

    @Valid
    private List<TrainerDTO> trainers;
}