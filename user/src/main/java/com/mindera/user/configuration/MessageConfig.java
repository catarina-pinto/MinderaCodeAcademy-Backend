package com.mindera.user.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindera.user.properties.AppProperties;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableRabbit
public class MessageConfig {
    @Value("${app.school.queue.seller}")
    private String queueSellerName;

    @Value("${app.school.queue.ordering}")
    private String queueOrderingName; 

    @Bean 
    public Queue queueSeller() { // Estamos a criar uma queue com o nome desejado 
        return new Queue(queueSellerName, true); 
    }

    @Bean 
    public Queue queueOrdering() { // Estamos a criar uma queue com o nome desejado 
        return new Queue(queueOrderingName, true); 
    }    
}
