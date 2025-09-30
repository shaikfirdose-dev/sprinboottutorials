package com.project.ecommerce.inventory_service.service;

import com.project.ecommerce.inventory_service.dto.OrderRequestDto;
import com.project.ecommerce.inventory_service.dto.OrderRequestItemDto;
import com.project.ecommerce.inventory_service.dto.ProductDto;
import com.project.ecommerce.inventory_service.entity.Product;
import com.project.ecommerce.inventory_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public List<ProductDto> getAllInventory() {
        log.info("Fetching all products from inventory");
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .toList();
    }

    public ProductDto getProductById(Long productId) {
        log.info("Fetching product with ID: {}", productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
        return modelMapper.map(product, ProductDto.class);
    }

    @Transactional
    public Double reduceStocks(OrderRequestDto orderRequestDto) {
        log.info("Reducing the stock for the order: {}", orderRequestDto);
        double totalPrice = 0.0;

        for(OrderRequestItemDto orderRequestItemDto : orderRequestDto.getOrderItems()){
            Product product = productRepository.findById(orderRequestItemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with ID: " + orderRequestItemDto.getProductId()));

            if(product.getStock() < orderRequestItemDto.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product ID: " + orderRequestItemDto.getProductId());
            }

            product.setStock(product.getStock() - orderRequestItemDto.getQuantity());
            productRepository.save(product);

            totalPrice += product.getPrice() * orderRequestItemDto.getQuantity();
        }

        return totalPrice;

    }

    @Transactional
    public String restockingInventory(OrderRequestDto orderRequestDto){
        log.info("Restocking the inventory for the order: {}", orderRequestDto);
        for(OrderRequestItemDto orderRequestItemDto : orderRequestDto.getOrderItems()){
            Product product = productRepository.findById(orderRequestItemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with ID: " + orderRequestItemDto.getProductId()));

            product.setStock(product.getStock() + orderRequestItemDto.getQuantity());
            productRepository.save(product);
        }

        return "Inventory restocked successfully";
    }
}
