package com.kafkapoc.orderservice.controller;

import com.kafkapoc.orderservice.dto.OrderDto;
import com.kafkapoc.orderservice.service.OrderProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderProducerService orderProducerService;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderDto orderDto) {
        log.info("Received order request: {}", orderDto);

        orderProducerService.sendOrder(orderDto);

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("" + orderDto.orderId());
    }
}
