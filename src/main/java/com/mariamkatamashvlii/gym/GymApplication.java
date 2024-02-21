package com.mariamkatamashvlii.gym;

import com.mariamkatamashvlii.gym.initializer.TraineeInit;
import com.mariamkatamashvlii.gym.initializer.TrainerInit;
import com.mariamkatamashvlii.gym.initializer.TrainingInit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class GymApplication {

    public static void main(String[] args) {
       // SpringApplication.run(GymApplication.class, args);
        ApplicationContext context = SpringApplication.run(GymApplication.class, args);
//        TraineeInit init = context.getBean(TraineeInit.class);
        //System.out.println(init.getDataPath());
        TraineeInit traineeInit = context.getBean(TraineeInit.class);
        traineeInit.getTraineeMap().values().forEach(System.out::println);
        TrainerInit trainerInit = context.getBean(TrainerInit.class);
        trainerInit.getTrainerMap().values().forEach(System.out::println);
        TrainingInit trainingInit = context.getBean(TrainingInit.class);
        trainingInit.getTrainingMap().values().forEach(System.out::println);
    }

}
