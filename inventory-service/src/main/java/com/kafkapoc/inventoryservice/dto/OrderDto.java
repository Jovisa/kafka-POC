package com.kafkapoc.inventoryservice.dto;

import java.math.BigDecimal;

public record OrderDto (
        Integer orderId,
        String productName,
        Integer quantity,
        BigDecimal price
) {}