package com.mariamkatamashvili.gym.repository;

import com.mariamkatamashvili.gym.entity.Training;
import lombok.Generated;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

@Generated
public interface TrainingRepository extends JpaRepository<Training, Long> {
    @Query("SELECT t FROM Training t " +
            "JOIN t.trainee tr " +
            "JOIN tr.user tu " +
            "JOIN t.trainer trr " +
            "JOIN trr.user tu2 " +
            "WHERE tu.username = :username " +
            "AND (:startDate IS NULL OR t.trainingDate >= :startDate) " +
            "AND (:endDate IS NULL OR t.trainingDate <= :endDate) " +
            "AND (:trainerName IS NULL OR tu2.username = :trainerName) " +
            "AND (:trainingTypeName IS NULL OR t.trainingType.trainingTypeName = :trainingTypeName)")
    List<Training> findTraineeTrainingsByCriteria(@Param("username") String username,
                                                  @Param("startDate") LocalDate startDate,
                                                  @Param("endDate") LocalDate endDate,
                                                  @Param("trainerName") String trainerName,
                                                  @Param("trainingTypeName") String trainingTypeName);

    @Query("SELECT t FROM Training t " +
            "JOIN t.trainer trr " +
            "JOIN trr.user tu2 " +
            "JOIN t.trainee tr " +
            "JOIN tr.user tu " +
            "WHERE tu2.username = :username " +
            "AND (:startDate IS NULL OR t.trainingDate >= :startDate) " +
            "AND (:endDate IS NULL OR t.trainingDate <= :endDate) " +
            "AND (:traineeName IS NULL OR tu.username = :traineeName)")
    List<Training> findTrainerTrainingsByCriteria(@Param("username") String username,
                                                  @Param("startDate") LocalDate startDate,
                                                  @Param("endDate") LocalDate endDate,
                                                  @Param("traineeName") String traineeName);
}