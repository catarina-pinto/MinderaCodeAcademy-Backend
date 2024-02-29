package com.mindera.ordering.message;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class SellerConsumer {

    @RabbitListener(queues = "${app.school.queue.seller}")
    public void receive(@Payload String body) {
        System.out.println(body);
    }
}
