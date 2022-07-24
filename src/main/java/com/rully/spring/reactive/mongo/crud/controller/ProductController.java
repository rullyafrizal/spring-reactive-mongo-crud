package com.rully.spring.reactive.mongo.crud.controller;

import com.rully.spring.reactive.mongo.crud.dto.ProductDto;
import com.rully.spring.reactive.mongo.crud.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public Flux<ProductDto> indexProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public Mono<ProductDto> getProduct(@PathVariable String id) {
        return productService.getProduct(id);
    }

    @GetMapping("filter-by-range")
    public Flux<ProductDto> getProduct(
        @RequestParam("min") Double min,
        @RequestParam("max") Double max
    ) {
        return productService.getProductInPriceRange(min, max);
    }

    @PostMapping
    public Mono<ProductDto> createProduct(
        @RequestBody Mono<ProductDto> productDtoMono
    ) {
        return productService.saveProduct(productDtoMono);
    }

    @PutMapping("/{id}/update")
    public Mono<ProductDto> updateProduct(
        @RequestBody Mono<ProductDto> productDtoMono,
        @PathVariable String id
    ) {
        return productService.updateProduct(productDtoMono, id);
    }

    @DeleteMapping("/{id}/delete")
    public Mono<Void> deleteProduct(
        @PathVariable String id
    ) {
        return productService.deleteProduct(id);
    }
}
