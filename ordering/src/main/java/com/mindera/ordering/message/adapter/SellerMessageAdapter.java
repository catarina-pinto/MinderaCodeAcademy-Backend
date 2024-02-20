package com.mindera.ordering.message.adapter;

import com.mindera.ordering.enums.Queues;
import com.mindera.ordering.model.QueueMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SellerMessageAdapter implements MessagePort {

    @Override
    public void processMessage(QueueMessage message) {
        log.info("Message arrived in class SellerMessageAdapter with body {}", message.getQueueMessage());
    }

    @Override
    public boolean canHandle(Queues queue) {
        return Queues.SELLER.equals(queue);
    }
}
