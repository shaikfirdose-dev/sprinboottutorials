package com.project.ecommerce.order_service.service;

import com.project.ecommerce.order_service.client.InventoryFeignClient;
import com.project.ecommerce.order_service.dto.OrderRequestDto;
import com.project.ecommerce.order_service.dto.OrderRequestItemDto;
import com.project.ecommerce.order_service.entity.OrderItem;
import com.project.ecommerce.order_service.entity.OrderStatus;
import com.project.ecommerce.order_service.entity.Orders;
import com.project.ecommerce.order_service.repository.OrdersRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Order;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final ModelMapper modelMapper;
    private final InventoryFeignClient inventoryFeignClient;

    public List<OrderRequestDto> getAllOrders() {
        log.info("Fetching all orders");
        return ordersRepository.findAll()
                .stream()
                .map(order -> modelMapper.map(order, OrderRequestDto.class))
                .toList();
    }

//    @Retry(name = "inventoryRetry", fallbackMethod = "createOrderFallback")
//    @RateLimiter(name = "inventoryRateLimiter", fallbackMethod = "createOrderFallback")
    @CircuitBreaker(name = "inventoryCircuitBreaker", fallbackMethod = "createOrderFallback")
    public OrderRequestDto createOrder(OrderRequestDto orderRequestDto) {
        log.info("Calling inventory service to reduce stock");
        Double totalPrice = inventoryFeignClient.reduceStocks(orderRequestDto);

        Orders order = modelMapper.map(orderRequestDto, Orders.class);
        for(OrderItem item : order.getOrderItems()) {
            item.setOrder(order);
        }
        order.setTotalPrice(totalPrice);
        order.setOrderStatus(OrderStatus.CONFIRMED);

        Orders savedOrders = ordersRepository.save(order);
        log.info("Order created with ID: {}", savedOrders.getId());

        return modelMapper.map(savedOrders, OrderRequestDto.class);
    }

    public OrderRequestDto createOrderFallback(OrderRequestDto orderRequestDto, Throwable throwable) {
        log.error("Failed to create order due to: {}. Please try again later.", throwable.getMessage());
        return new OrderRequestDto();
    }
}
