package com.mariamkatamashvili.gym.health;

import com.mariamkatamashvili.gym.generator.PasswordGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.actuate.health.Status;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
class PasswordGeneratorHealthIndicatorTest {
    @Test
    void whenPasswordIsGenerated_thenHealthIsUp() {
        // Given
        PasswordGenerator mockPasswordGenerator = Mockito.mock(PasswordGenerator.class);
        when(mockPasswordGenerator.generatePassword()).thenReturn("StrongPassword123");

        PasswordGeneratorHealthIndicator healthIndicator = new PasswordGeneratorHealthIndicator(mockPasswordGenerator);

        // When
        var health = healthIndicator.health();

        // Then
        assertThat(health.getStatus()).isEqualTo(Status.UP);
    }

    @Test
    void whenPasswordIsEmpty_thenHealthIsDown() {
        // Given
        PasswordGenerator mockPasswordGenerator = Mockito.mock(PasswordGenerator.class);
        when(mockPasswordGenerator.generatePassword()).thenReturn("");

        PasswordGeneratorHealthIndicator healthIndicator = new PasswordGeneratorHealthIndicator(mockPasswordGenerator);

        // When
        var health = healthIndicator.health();

        // Then
        assertThat(health.getStatus()).isEqualTo(Status.DOWN);
        assertThat(health.getDetails()).containsEntry("error", "Generation failure");
    }

    @Test
    void whenPasswordGenerationThrowsException_thenHealthIsDown() {
        // Given
        PasswordGenerator mockPasswordGenerator = Mockito.mock(PasswordGenerator.class);
        when(mockPasswordGenerator.generatePassword()).thenThrow(new RuntimeException("Error during password generation"));

        PasswordGeneratorHealthIndicator healthIndicator = new PasswordGeneratorHealthIndicator(mockPasswordGenerator);

        // When
        var health = healthIndicator.health();

        // Then
        assertThat(health.getStatus()).isEqualTo(Status.DOWN);
        assertThat(health.getDetails()).containsKey("error");
    }
}