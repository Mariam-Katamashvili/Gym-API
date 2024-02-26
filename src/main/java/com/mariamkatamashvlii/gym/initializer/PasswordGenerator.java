package com.mariamkatamashvlii.gym.initializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class PasswordGenerator {
    private static final Logger logger = LoggerFactory.getLogger(TraineeInit.class);
    public static String randomPasswordGenerator() {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            char randomChar = (char) random.nextInt(33, 127);
            builder.append(randomChar);
        }
        logger.info("Generated password");
        return builder.toString();
    }
}
