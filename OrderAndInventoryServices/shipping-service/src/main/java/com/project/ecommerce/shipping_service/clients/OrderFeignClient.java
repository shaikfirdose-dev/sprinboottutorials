package com.project.ecommerce.shipping_service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "order-service", path = "/orders")
public interface OrderFeignClient {

    @PutMapping("/core/update-order/{orderId}")
    void updateOrder(@PathVariable Long orderId);

}
