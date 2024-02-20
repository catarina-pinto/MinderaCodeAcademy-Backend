package com.mindera.ordering.message.adapter;

import com.mindera.ordering.enums.Queues;
import com.mindera.ordering.model.QueueMessage;

public interface MessagePort {
    void processMessage(QueueMessage queueMessage);
    boolean canHandle(Queues queue);
}
