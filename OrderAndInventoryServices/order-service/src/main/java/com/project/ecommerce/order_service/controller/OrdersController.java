package com.project.ecommerce.order_service.controller;

import com.project.ecommerce.order_service.dto.OrderRequestDto;
import com.project.ecommerce.order_service.entity.Orders;
import com.project.ecommerce.order_service.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
@RefreshScope 
public class OrdersController {

    private final OrdersService ordersService;

    @GetMapping("/helloOrders")
    public String helloOrders(@RequestHeader("X-User-Id") Long userId) {
        return "Hello from Orders Service with User ID: " + userId;
    }

    @GetMapping
    public ResponseEntity<List<OrderRequestDto>> getAllOrders() {
        return ResponseEntity.ok(ordersService.getAllOrders());
    }

    @PostMapping("/create-order")
    public ResponseEntity<OrderRequestDto> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        OrderRequestDto createdOrder = ordersService.createOrder(orderRequestDto);
        return ResponseEntity.ok(createdOrder);
    }

    @PostMapping("/cancel-order/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
        String response = ordersService.cancelOrder(orderId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-order/{orderId}")
    public ResponseEntity<Void> updateOrder(@PathVariable Long orderId) {
        ordersService.updateOrder(orderId);
        return ResponseEntity.ok().build();
    }
}
