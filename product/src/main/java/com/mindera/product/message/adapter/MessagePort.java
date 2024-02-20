package com.mindera.product.message.adapter;

import com.mindera.product.model.QueueMessage;
import com.mindera.product.enums.Queues;

public interface MessagePort {
    void processMessage(QueueMessage queueMessage);
    boolean canHandle(Queues queue);
}
