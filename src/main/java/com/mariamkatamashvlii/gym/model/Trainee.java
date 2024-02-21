package com.mariamkatamashvlii.gym.model;

import java.time.LocalDate;

public class Trainee extends User {
    private LocalDate dob;
    private String address;
    private long userId;

    public Trainee(String firstName, String lastName, String username, String password, boolean isActive, LocalDate dob, String address, long userId) {
        super(firstName, lastName, username, password, isActive);
        this.dob = dob;
        this.address = address;
        this.userId = userId;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return super.toString() + "Trainee{" +
                "dob=" + dob +
                ", address='" + address + '\'' +
                ", userId=" + userId +
                '}';
    }
}
