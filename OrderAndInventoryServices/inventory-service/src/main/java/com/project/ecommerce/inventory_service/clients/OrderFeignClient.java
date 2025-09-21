package com.project.ecommerce.inventory_service.clients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "order-service", path = "/orders")   //name would be name of the service and path would be context path
public interface OrderFeignClient {

    @GetMapping("/core/helloOrders")    // here method url
    String helloOrders();
}
