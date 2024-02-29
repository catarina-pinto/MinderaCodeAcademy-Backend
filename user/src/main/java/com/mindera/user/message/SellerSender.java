package com.mindera.user.message;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SellerSender {
    private final RabbitTemplate rabbitTemplate;
    private final Queue queueSeller;

    public void send(String message) {
        rabbitTemplate.convertAndSend(queueSeller.getName(), message);
    }
}