package com.mariamkatamashvili.gym.dto.traineeDto;

import com.mariamkatamashvili.gym.dto.trainerDto.TrainerUsernameDTO;
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