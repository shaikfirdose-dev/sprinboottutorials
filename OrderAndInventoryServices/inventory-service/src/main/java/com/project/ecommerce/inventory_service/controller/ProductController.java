package com.project.ecommerce.inventory_service.controller;


import com.project.ecommerce.inventory_service.clients.OrderFeignClient;
import com.project.ecommerce.inventory_service.dto.OrderRequestDto;
import com.project.ecommerce.inventory_service.dto.ProductDto;
import com.project.ecommerce.inventory_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;

    private final OrderFeignClient orderFeignClient;

    @GetMapping("/fetchOrders")
    public String fetchOrders(){
        log.info("Fetching orders from order-service using FeignClient");
//        ServiceInstance instance = discoveryClient.getInstances("order-service").getFirst();
//
//        return restClient.get()
//                .uri(instance.getUri()+"/orders/core/helloOrders")
//                .retrieve()
//                .body(String.class);

        return orderFeignClient.helloOrders();
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllInventory() {
        List<ProductDto> products = productService.getAllInventory();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(Long productId) {
        ProductDto product = productService.getProductById(productId);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/reduce-stocks")
    public ResponseEntity<Double> reduceStocks(@RequestBody OrderRequestDto orderRequestDto) {
        Double totalPrice = productService.reduceStocks(orderRequestDto);
        return ResponseEntity.ok(totalPrice);
    }

    @PutMapping("/restock-inventory")
    public ResponseEntity<String> restockingInventory(@RequestBody OrderRequestDto orderRequestDto) {
        String response = productService.restockingInventory(orderRequestDto);
        return ResponseEntity.ok(response);
    }

}
