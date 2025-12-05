package com.kafkapoc.orderservice.service;

import com.kafkapoc.orderservice.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderProducerService {

    private final KafkaTemplate<String, OrderDto> kafkaTemplate;

    @Value("${app.kafka.topic.orders}")
    private String ordersTopic;

    public void sendOrder(OrderDto orderDto) {
        log.info("Sending Order to Kafka topic '{}': {}", ordersTopic, orderDto);

        kafkaTemplate.send(ordersTopic, String.valueOf(orderDto.orderId()), orderDto)
                .whenComplete((result, exception) -> {
                    if (exception == null) {
                        log.info("Order sent succesfully: orderId={}, offset={}",
                                orderDto.orderId(),
                                result.getRecordMetadata().offset());
                    } else {
                        log.error("Failed to send Order: orderId={}", orderDto.orderId(), exception);
                    }
                });
    }
}
