package com.project.ecommerce.shipping_service.service;

import com.project.ecommerce.shipping_service.clients.OrderFeignClient;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShippingService {

    private final OrderFeignClient orderFeignClient;


    @Retry(name = "shippingServiceRetry", fallbackMethod = "shipOrderFallback")
    @RateLimiter(name="shippingServiceRateLimiter", fallbackMethod = "shipOrderFallback")
    public String shipOrder(Long orderId){
        log.info("Shipping order with ID: {}", orderId);
        orderFeignClient.updateOrder(orderId);
        log.info("Order with ID: {} has been shipped and order status updated to SHIPPED in Order Service", orderId);
        return "Order with ID: " + orderId + " has been shipped.";
    }

    public String shipOrderFallback(Long orderId, Throwable throwable) {
        log.error("Failed to ship order with ID: {} after retries. Error: {}", orderId, throwable.getMessage());
        return "Failed to ship order with ID: " + orderId + " after retries.";
    }
}
