package com.project.ecommerce.inventory_service.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

    private Long id;
    private String title;
    private String description;
    private Double price;
    private Integer stock;
}
