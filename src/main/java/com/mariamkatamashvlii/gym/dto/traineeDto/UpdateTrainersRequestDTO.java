package com.mariamkatamashvlii.gym.dto.traineeDto;

import com.mariamkatamashvlii.gym.dto.trainerDto.TrainerUsernameDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTrainersRequestDTO {
    private String username;
    List<TrainerUsernameDTO> trainers;
}