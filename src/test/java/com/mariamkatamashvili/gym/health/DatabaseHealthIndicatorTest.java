package com.mariamkatamashvili.gym.health;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.actuate.health.Status;

import javax.sql.DataSource;
import java.sql.Connection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
class DatabaseHealthIndicatorTest {
    @Test
    void whenDatabaseIsReachable_thenHealthIsUp() throws Exception {
        // Given
        DataSource mockDataSource = Mockito.mock(DataSource.class);
        Connection mockConnection = Mockito.mock(Connection.class);
        when(mockDataSource.getConnection()).thenReturn(mockConnection);
        when(mockConnection.isValid(1)).thenReturn(true);

        DatabaseHealthIndicator healthIndicator = new DatabaseHealthIndicator(mockDataSource);

        // When
        var health = healthIndicator.health();

        // Then
        assertThat(health.getStatus()).isEqualTo(Status.UP);
        assertThat(health.getDetails()).containsEntry("database", "Active and reachable");
    }

    @Test
    void whenDatabaseIsNotReachable_thenHealthIsDown() throws Exception {
        // Given
        DataSource mockDataSource = Mockito.mock(DataSource.class);
        Connection mockConnection = Mockito.mock(Connection.class);
        when(mockDataSource.getConnection()).thenReturn(mockConnection);
        when(mockConnection.isValid(1)).thenReturn(false);

        DatabaseHealthIndicator healthIndicator = new DatabaseHealthIndicator(mockDataSource);

        // When
        var health = healthIndicator.health();

        // Then
        assertThat(health.getStatus()).isEqualTo(Status.DOWN);
        assertThat(health.getDetails()).containsEntry("database", "Not reachable");
    }

    @Test
    void whenDatabaseConnectionThrowsException_thenHealthIsDown() throws Exception {
        // Given
        DataSource mockDataSource = Mockito.mock(DataSource.class);
        when(mockDataSource.getConnection()).thenThrow(new RuntimeException("Connection failed"));

        DatabaseHealthIndicator healthIndicator = new DatabaseHealthIndicator(mockDataSource);

        // When
        var health = healthIndicator.health();

        // Then
        assertThat(health.getStatus()).isEqualTo(Status.DOWN);
        assertThat(health.getDetails())
                .containsEntry("database", "Error when checking database connectivity")
                .containsKey("error");
    }
}