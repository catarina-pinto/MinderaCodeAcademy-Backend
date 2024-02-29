package com.mindera.product.message;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderingSender {
    private final RabbitTemplate rabbitTemplate;
    private final Queue queueOrdering;

    public void send(String message) {
        rabbitTemplate.convertAndSend(queueOrdering.getName(), message);
    }
}
