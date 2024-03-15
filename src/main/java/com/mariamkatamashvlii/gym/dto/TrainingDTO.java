package com.mariamkatamashvlii.gym.dto;

import com.mariamkatamashvlii.gym.entity.TrainingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingDTO {
    private String trainingName;
    private Date date;
    private TrainingType trainingType;
    private Number duration;
    private String name;
}
