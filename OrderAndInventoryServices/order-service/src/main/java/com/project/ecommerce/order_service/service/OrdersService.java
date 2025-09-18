package com.project.ecommerce.order_service.service;

import com.project.ecommerce.order_service.dto.OrderRequestDto;
import com.project.ecommerce.order_service.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final ModelMapper modelMapper;

    public List<OrderRequestDto> getAllOrders() {
        log.info("Fetching all orders");
        return ordersRepository.findAll()
                .stream()
                .map(order -> modelMapper.map(order, OrderRequestDto.class))
                .toList();
    }
}
