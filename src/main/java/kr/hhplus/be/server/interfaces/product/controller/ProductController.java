package kr.hhplus.be.server.interfaces.product.controller;

import kr.hhplus.be.server.domain.product.Product;
import kr.hhplus.be.server.interfaces.ApiResponse;
import kr.hhplus.be.server.interfaces.product.dto.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    @GetMapping
    public ApiResponse<ProductResponse> getProductList(){
        Product product1 = new Product(1L, "상품A", 13000L, 22L);
        Product product2 = new Product(2L, "상품B", 18000L, 50L);
        return  ApiResponse.ok(new ProductResponse("조회 성공", List.of(product1, product2)));
    }

    @GetMapping("/best")
    public ApiResponse<ProductResponse> getBestProductList(){
        Product product1 = new Product(1L, "상품A", 13000L, 22L);
        Product product2 = new Product(2L, "상품B", 18000L, 50L);
        return  ApiResponse.ok(new ProductResponse("조회 성공", List.of(product1, product2)));
    }

}
