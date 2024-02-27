package com.mariamkatamashvlii.gym;

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
//        TraineeInit traineeInit = context.getBean(TraineeInit.class);
//        traineeInit.getTraineeMap().values().forEach(System.out::println);
//        TrainerInit trainerInit = context.getBean(TrainerInit.class);
//        trainerInit.getTrainerMap().values().forEach(System.out::println);
//        TrainingInit trainingInit = context.getBean(TrainingInit.class);
//        trainingInit.getTrainingMap().values().forEach(System.out::println);
    }
    //use logger for printing and remove commented lines todo
    //Anyway, for calling this run in the project
    // you'd need to override method run and put there your runtime instructions.

}
