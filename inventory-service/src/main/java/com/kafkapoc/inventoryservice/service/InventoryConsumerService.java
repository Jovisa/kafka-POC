package com.kafkapoc.inventoryservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafkapoc.inventoryservice.dto.OrderDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class InventoryConsumerService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(
            topics = "${app.kafka.topic.orders}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void consumeOrder(String message,
                             @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                             @Header(KafkaHeaders.OFFSET) long offset) {
        try {
            OrderDto order = objectMapper.readValue(message, OrderDto.class);

            log.info("==============================================");
            log.info("Received Order from Kafka:");
            log.info("Order id: {}", order.orderId());
            log.info(order.toString());
            log.info("Partition: {}, Offset {}", partition, offset);
            log.info("Update Inventory for: {}", order.productName());
            log.info("==============================================");
        } catch (Exception e) {
            log.error("Failed to parse message: {}", message, e);
        }
    }
}
