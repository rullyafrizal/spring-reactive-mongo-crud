package com.rully.spring.reactive.mongo.crud.service;

import com.rully.spring.reactive.mongo.crud.dto.ProductDto;
import com.rully.spring.reactive.mongo.crud.repository.ProductRepository;
import com.rully.spring.reactive.mongo.crud.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Flux<ProductDto> getProducts() {
        return productRepository.findAll().map(AppUtils::entityToDto);
    }

    public Mono<ProductDto> getProduct(String id) {
        return productRepository.findById(id).map(AppUtils::entityToDto);
    }

    public Flux<ProductDto> getProductInPriceRange(Double minPrice, Double maxPrice) {
        return productRepository.findByPriceBetween(Range.closed(minPrice, maxPrice));
    }

    public Mono<ProductDto> saveProduct(Mono<ProductDto> productDto) {
        return productDto.map(AppUtils::dtoToEntity)
            .flatMap(productRepository::insert)
            .map(AppUtils::entityToDto);
    }

    public Mono<ProductDto> updateProduct(Mono<ProductDto> productDtoMono, String id) {
        return productRepository.findById(id)
            .flatMap(p -> productDtoMono.map(AppUtils::dtoToEntity)
                .doOnNext(e -> e.setId(id)))
            .flatMap(productRepository::save)
            .map(AppUtils::entityToDto);
    }

    public Mono<Void> deleteProduct(String id) {
        return productRepository.deleteById(id);
    }
}
