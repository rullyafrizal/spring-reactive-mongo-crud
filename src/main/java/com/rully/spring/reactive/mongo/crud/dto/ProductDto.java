package com.rully.spring.reactive.mongo.crud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private String id;
    private String name;
    private Integer quantity;
    private Double price;

}
