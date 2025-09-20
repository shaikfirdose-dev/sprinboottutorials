package com.project.ecommerce.order_service.controller;

import com.project.ecommerce.order_service.dto.OrderRequestDto;
import com.project.ecommerce.order_service.entity.Orders;
import com.project.ecommerce.order_service.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;

    @GetMapping("/helloOrders")
    public String helloOrders() {
        return "Hello from Orders Service";
    }

    @GetMapping
    public ResponseEntity<List<OrderRequestDto>> getAllOrders() {
        return ResponseEntity.ok(ordersService.getAllOrders());
    }
}
