package com.mindera.product.controller;

import com.mindera.product.message.SellerSender;
import com.mindera.product.model.QueueMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {
    private final SellerSender sellerSender;

    @GetMapping
    public void publishMessage(@RequestParam String queueMessage) {
        sellerSender.send(queueMessage);
    }

}