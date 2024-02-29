package com.mindera.ordering.message;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;

public class OrderingConsumer {
    @RabbitListener(queues = "${app.school.queue.ordering}")
    public void receive(@Payload String body) {
        System.out.println(body);
    }
}
