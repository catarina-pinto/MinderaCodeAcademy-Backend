package com.mindera.product.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
