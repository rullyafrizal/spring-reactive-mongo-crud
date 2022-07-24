package com.rully.spring.reactive.mongo.crud.repository;

import com.rully.spring.reactive.mongo.crud.dto.ProductDto;
import com.rully.spring.reactive.mongo.crud.entity.Product;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

    Flux<ProductDto> findByPriceBetween(Range<Double> closed);

}
