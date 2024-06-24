package com.mariamkatamashvili.gym.messaging;

import com.mariamkatamashvili.gym.config.jms.TestConfig;
import com.mariamkatamashvili.gym.dto.ActionType;
import com.mariamkatamashvili.gym.dto.WorkloadDTO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@CucumberContextConfiguration
@ContextConfiguration(classes = {TestConfig.class})
public class MessageProducerTest {

    @Autowired
    private MessageProducer messageProducer;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Given("gym-api is running")
    public void givenGymApiIsRunning() {
    }

    @When("a WorkloadDTO is sent to ActiveMQ")
    public void whenWorkloadDtoIsSentToActiveMQ() {
        WorkloadDTO workloadDTO = WorkloadDTO.builder()
                .username("johndoe")
                .firstName("John")
                .lastName("Doe")
                .isActive(true)
                .date(LocalDate.now())
                .duration(60)
                .actionType(ActionType.ADD)
                .build();

        messageProducer.sendMessage(workloadDTO);
    }

    @Then("the message should be in the {string} queue")
    public void thenTheMessageShouldBeInTheQueue(String queueName) throws JMSException {
        System.out.println("Waiting to receive message from queue...");
        Message message = jmsTemplate.receive(queueName);
        assertNotNull(message, "Message should not be null");
        assertInstanceOf(TextMessage.class, message, "Message should be of type TextMessage");

        TextMessage textMessage = (TextMessage) message;
        String received = textMessage.getText();
        System.out.println("Received message: " + received);

        assertTrue(received.contains("johndoe"), "Message should contain 'johndoe'");
        assertTrue(received.contains("John"), "Message should contain 'John'");
        assertTrue(received.contains("Doe"), "Message should contain 'Doe'");
    }
}