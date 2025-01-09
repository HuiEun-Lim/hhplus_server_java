package kr.hhplus.be.server.interfaces.web.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.application.product.dto.ProductFacadeResponse;
import kr.hhplus.be.server.application.product.facade.ProductFacade;
import kr.hhplus.be.server.domain.product.entity.Product;
import kr.hhplus.be.server.interfaces.web.ApiResponse;
import kr.hhplus.be.server.interfaces.web.product.dto.request.ProductRequest;
import kr.hhplus.be.server.interfaces.web.product.dto.response.ProductResponse;
import kr.hhplus.be.server.interfaces.web.product.dto.response.TopProductResponse;
import kr.hhplus.be.server.interfaces.web.product.model.ProductInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "상품 관리", description = "상품 관리")
@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductFacade productFacade;

    @Operation(summary = "상품 목록 조회", description = "상품 목록을 조회한다.")
    @GetMapping
    public ApiResponse<ProductResponse> getProductList(@RequestBody ProductRequest request){
        Page<ProductFacadeResponse> products = productFacade.getProducts(request.getPageable());
        return  ApiResponse.ok(new ProductResponse("조회 성공", products.map(ProductInfo::toInfo)));
    }

    @Operation(summary = "상위 상품 목록 조회", description = "최근 3일간 판매량 상위 상품 5개 목록을 조회한다.")
    @GetMapping("/best")
    public ApiResponse<TopProductResponse> getBestProductList(){
        Product product1 = new Product(1L, "상품A", 13000L);
        Product product2 = new Product(2L, "상품B", 18000L);
        return  ApiResponse.ok(new TopProductResponse("조회 성공", List.of(product1, product2)));
    }

}
