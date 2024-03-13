package com.mariamkatamashvlii.gym.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class TraineeProfileDTO {
   private String firstName;
   private String lastName;
   private Date birthday;
   private String address;
   private boolean isActive;
   private List<TrainerDTO> trainers;
}
