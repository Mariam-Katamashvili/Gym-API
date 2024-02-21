package com.mariamkatamashvlii.gym.model;

public class Trainer extends User {
    private String specialization;
    private long userId;

    public Trainer(String firstName, String lastName, String username, String password, boolean isActive, String specialization, long userId) {
        super(firstName, lastName, username, password, isActive);
        this.specialization = specialization;
        this.userId = userId;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return super.toString() + "Trainer{" +
                "specialization='" + specialization + '\'' +
                ", userId=" + userId +
                '}';
    }
}
