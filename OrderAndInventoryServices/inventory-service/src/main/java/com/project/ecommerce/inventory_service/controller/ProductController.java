package com.project.ecommerce.inventory_service.controller;


import com.project.ecommerce.inventory_service.dto.ProductDto;
import com.project.ecommerce.inventory_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;

    @GetMapping("/fetchOrders")
    public String fetchOrders(){
        ServiceInstance instance = discoveryClient.getInstances("order-service").getFirst();

        return restClient.get()
                .uri(instance.getUri()+"/api/v1/orders/helloOrders")
                .retrieve()
                .body(String.class);
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

}
