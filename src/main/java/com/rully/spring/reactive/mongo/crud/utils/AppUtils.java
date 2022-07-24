package com.rully.spring.reactive.mongo.crud.utils;

import com.rully.spring.reactive.mongo.crud.dto.ProductDto;
import com.rully.spring.reactive.mongo.crud.entity.Product;
import org.springframework.beans.BeanUtils;

public class AppUtils {

    public static ProductDto entityToDto(Product product) {
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product, productDto);

        return productDto;
    }

    public static Product dtoToEntity(ProductDto productDto) {
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);

        return product;
    }

}
