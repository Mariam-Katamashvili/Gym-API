package com.mariamkatamashvili.gym.config.jms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mariamkatamashvili.gym.messaging.MessageProducer;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@EnableJms
@TestConfiguration
public class TestConfig {
    @Bean
    public CachingConnectionFactory cachingConnectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        String brokerUrl = "tcp://localhost:61616";
        activeMQConnectionFactory.setBrokerURL(brokerUrl);
        String userName = "admin";
        activeMQConnectionFactory.setUserName(userName);
        String password = "admin";
        activeMQConnectionFactory.setPassword(password);

        return new CachingConnectionFactory(activeMQConnectionFactory);
    }

    @Bean
    public JmsTemplate jmsTemplate(
            CachingConnectionFactory cachingConnectionFactory,
            MessageConverter messageConverter
    ) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(cachingConnectionFactory);
        jmsTemplate.setMessageConverter(messageConverter);
        return jmsTemplate;
    }

    @Bean
    public MessageConverter messageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setObjectMapper(objectMapper);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    @Bean
    public MessageProducer messageProducer(JmsTemplate jmsTemplate) {
        return new MessageProducer(jmsTemplate);
    }
}