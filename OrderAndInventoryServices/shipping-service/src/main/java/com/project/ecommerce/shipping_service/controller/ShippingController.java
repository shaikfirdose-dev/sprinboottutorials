package com.project.ecommerce.shipping_service.controller;


import com.project.ecommerce.shipping_service.service.ShippingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class ShippingController {

    private final ShippingService shippingService;

    @PutMapping("/ship-order/{orderId}")
    public ResponseEntity<String> shipOrder(@PathVariable Long orderId){
        String response = shippingService.shipOrder(orderId);
        return ResponseEntity.ok(response);
    }


}
