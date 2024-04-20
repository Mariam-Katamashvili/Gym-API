package com.mariamkatamashvlii.gym.dto.traineeDto;

import com.mariamkatamashvlii.gym.dto.trainerDto.TrainerDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
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
