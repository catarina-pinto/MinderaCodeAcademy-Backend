package com.mindera.ordering.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindera.ordering.message.MessageBrokerReceiver;
import com.mindera.ordering.properties.AppProperties;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class MessageConfig {
    @Bean
    public CachingConnectionFactory connectionFactory(AppProperties properties) {
        var rabbit = properties.getRabbit();
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(
                rabbit.getUrl() , rabbit.getPort()
        );
        cachingConnectionFactory.setUsername(rabbit.getUsername());
        cachingConnectionFactory.setPassword(rabbit.getPassword());
        cachingConnectionFactory.setConnectionTimeout(rabbit.getConnectionTimeout());
        return cachingConnectionFactory;
    }

    @Bean
    public AmqpAdmin brokerDefaultRabbitMQAdmin(ConnectionFactory connectionFactory, AppProperties properties) {
        AmqpAdmin amqpAdmin = new RabbitAdmin(connectionFactory);
        List<String> queues = properties.getRabbit().getQueues();

        for (String queue : queues) {
            Map<String, Object> queueArguments = new HashMap<>();
            Queue newQueue = new Queue(queue, true, false, false, queueArguments);
            amqpAdmin.declareQueue(newQueue);
        }

        return amqpAdmin;
    }

    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory, MessageBrokerReceiver messageBrokerReceiver, AppProperties properties) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();

        var rabbit = properties.getRabbit();

        container.setConnectionFactory(connectionFactory);
        container.setQueueNames("SELLER", "ORDERING");
        container.setMessageListener(messageBrokerReceiver);
        container.setMaxConcurrentConsumers(rabbit.getMaxConcurrentConsumer());
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setMissingQueuesFatal(false);
        container.setFailedDeclarationRetryInterval(rabbit.getRetryInterval());

        return container;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(ObjectMapper objectMapper) {
        objectMapper.setDefaultPropertyInclusion(
                JsonInclude.Value.construct(JsonInclude.Include.ALWAYS, JsonInclude.Include.NON_NULL)
        );

        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, ObjectMapper objectMapper) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter(objectMapper));
        return rabbitTemplate;
    }
}
