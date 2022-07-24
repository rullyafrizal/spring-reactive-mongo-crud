package com.rully.spring.reactive.mongo.crud;

import com.rully.spring.reactive.mongo.crud.controller.ProductController;
import com.rully.spring.reactive.mongo.crud.dto.ProductDto;
import com.rully.spring.reactive.mongo.crud.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebFluxTest(ProductController.class)
class SpringReactiveMongoCrudApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private ProductService productService;

	@Test
	void testAddProduct() {
		Mono<ProductDto> productDtoMono = Mono.just(new ProductDto("102", "Mobile", 11, 10000D));

		when(productService.saveProduct(productDtoMono))
			.thenReturn(productDtoMono);

		webTestClient.post()
			.uri("/products")
			.body(Mono.just(productDtoMono), ProductDto.class)
			.exchange()
			.expectStatus().isOk();

		verify(productService, times(1))
			.saveProduct(any());
	}

	@Test
	void testGetProducts() {
		Flux<ProductDto> productDtoFlux = Flux.just(
			new ProductDto("102", "Mobile", 11, 10000D),
			new ProductDto("103", "TV", 10, 15000D)
		);

		when(productService.getProducts())
			.thenReturn(productDtoFlux);

		Flux<ProductDto> responseBody = webTestClient.get()
			.uri("/products")
			.exchange()
			.expectStatus().isOk()
			.returnResult(ProductDto.class)
			.getResponseBody();

		verify(productService, times(1))
			.getProducts();

		StepVerifier.create(responseBody)
			.expectSubscription()
			.expectNext(new ProductDto("102", "Mobile", 11, 10000D))
			.expectNext(new ProductDto("103", "TV", 10, 15000D))
			.verifyComplete();
	}

	@Test
	void getProductTest() {
		Mono<ProductDto> productDtoMono = Mono.just(new ProductDto("102", "Mobile", 11, 10000D));

		when(productService.getProduct(any()))
			.thenReturn(productDtoMono);

		Flux<ProductDto> responseBody = webTestClient.get()
			.uri("/products/102")
			.exchange()
			.expectStatus().isOk()
			.returnResult(ProductDto.class)
			.getResponseBody();

		verify(productService, times(1)).getProduct(any());

		StepVerifier.create(responseBody)
			.expectSubscription()
			.expectNextMatches(p -> p.getName().equals("Mobile"))
			.verifyComplete();
	}

	@Test
	void testUpdateProduct() {
		Mono<ProductDto> productDtoMono = Mono.just(new ProductDto("102", "Mobile", 11, 10000D));

		when(productService.updateProduct(productDtoMono, "102"))
			.thenReturn(productDtoMono);

		Flux<ProductDto> responseBody = webTestClient.put()
			.uri("/products/102/update")
			.exchange()
			.expectStatus().isOk()
			.returnResult(ProductDto.class)
			.getResponseBody();

		verify(productService, times(1)).updateProduct(any(), any());

		StepVerifier.create(responseBody)
			.expectSubscription()
			.verifyComplete();
	}

	@Test
	void testDeleteProduct() {
		given(productService.deleteProduct(any()))
			.willReturn(Mono.empty());

		webTestClient.delete()
			.uri("/products/102/delete")
			.exchange()
			.expectStatus().isOk();

		verify(productService, times(1)).deleteProduct(any());
	}

}
