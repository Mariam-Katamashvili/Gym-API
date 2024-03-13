package com.mariamkatamashvlii.gym.dto;

import com.mariamkatamashvlii.gym.entity.TrainingType;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Date;

@Data
@RequiredArgsConstructor
public class TrainingDTO {
    private String trainingName;
    private Date date;
    private TrainingType trainingType;
    private Number duration;
    private String name;
}
