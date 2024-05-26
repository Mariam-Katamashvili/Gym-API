package com.mariamkatamashvlii.gym.service;

public interface LoginAttemptsService {
    void loginFailed(String username);

    void loginSucceeded(String username);

    boolean isLockedOut(String username);
}