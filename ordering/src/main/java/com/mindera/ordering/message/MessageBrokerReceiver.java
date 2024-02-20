package com.mindera.ordering.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindera.ordering.enums.Queues;
import com.mindera.ordering.message.adapter.MessagePort;
import com.mindera.ordering.model.QueueMessage;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageBrokerReceiver implements ChannelAwareMessageListener {
    private final ObjectMapper objectMapper;
    private final List<MessagePort> messagePorts;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        QueueMessage queueMessage = objectMapper.readValue(message.getBody(), QueueMessage.class);

        log.info("Message received from queue {} ", message.getMessageProperties().getConsumerQueue());
        log.info("Body received {} ", queueMessage.getQueueMessage());
        log.info("Host name {} ", channel.getConnection().getAddress().getHostName());

        messagePorts.stream().filter(messagePort -> messagePort.canHandle(Queues.valueOf(message.getMessageProperties().getConsumerQueue())))
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .processMessage(queueMessage);

        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
