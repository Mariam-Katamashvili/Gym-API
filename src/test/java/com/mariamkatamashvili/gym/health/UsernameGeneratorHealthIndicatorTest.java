package com.mariamkatamashvili.gym.health;

import com.mariamkatamashvili.gym.generator.UsernameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.actuate.health.Status;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class UsernameGeneratorHealthIndicatorTest {
    @Test
    void whenUsernameIsGenerated_thenHealthIsUp() {
        // Given
        UsernameGenerator mockUsernameGenerator = Mockito.mock(UsernameGenerator.class);
        when(mockUsernameGenerator.generateUsername("Test", "User")).thenReturn("TestUser");

        UsernameGeneratorHealthIndicator healthIndicator = new UsernameGeneratorHealthIndicator(mockUsernameGenerator);

        // When
        var health = healthIndicator.health();

        // Then
        assertThat(health.getStatus()).isEqualTo(Status.UP);
    }

    @Test
    void whenUsernameIsEmpty_thenHealthIsDown() {
        // Given
        UsernameGenerator mockUsernameGenerator = Mockito.mock(UsernameGenerator.class);
        when(mockUsernameGenerator.generateUsername("Test", "User")).thenReturn("");

        UsernameGeneratorHealthIndicator healthIndicator = new UsernameGeneratorHealthIndicator(mockUsernameGenerator);

        // When
        var health = healthIndicator.health();

        // Then
        assertThat(health.getStatus()).isEqualTo(Status.DOWN);
        assertThat(health.getDetails()).containsEntry("error", "Generation failure");
    }

    @Test
    void whenUsernameGenerationThrowsException_thenHealthIsDown() {
        // Given
        UsernameGenerator mockUsernameGenerator = Mockito.mock(UsernameGenerator.class);
        when(mockUsernameGenerator.generateUsername("Test", "User")).thenThrow(new RuntimeException("Error during username generation"));

        UsernameGeneratorHealthIndicator healthIndicator = new UsernameGeneratorHealthIndicator(mockUsernameGenerator);

        // When
        var health = healthIndicator.health();

        // Then
        assertThat(health.getStatus()).isEqualTo(Status.DOWN);
        assertThat(health.getDetails()).containsKey("error");
    }
}