package com.mindera.product.message.adapter;

import com.mindera.product.enums.Queues;
import com.mindera.product.model.QueueMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderingMessageAdapter implements MessagePort {
    @Override
    public void processMessage(QueueMessage message) {
        log.info("Message arrived in class SellerMessageAdapter with body {}", message.getQueueMessage());
    }

    @Override
    public boolean canHandle(Queues queue) {
        return Queues.ORDERING.equals(queue);
    }
}