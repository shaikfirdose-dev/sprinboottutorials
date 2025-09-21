package com.project.ecommerce.inventory_service.dto;

import lombok.Data;

import java.util.List;
import com.project.ecommerce.inventory_service.dto.OrderRequestItemDto;

@Data
public class OrderRequestDto {
    private List<OrderRequestItemDto> orderItems;
}
