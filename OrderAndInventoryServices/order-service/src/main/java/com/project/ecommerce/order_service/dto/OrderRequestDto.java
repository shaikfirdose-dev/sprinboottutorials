package com.project.ecommerce.order_service.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderRequestDto {

    private Long id;
    private List<OrderRequestItemDto> orderItems;
    private BigDecimal totalPrice;
}
