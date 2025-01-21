package kr.hhplus.be.server.interfaces.web.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.application.product.dto.ProductFacadeResponse;
import kr.hhplus.be.server.application.product.facade.ProductFacade;
import kr.hhplus.be.server.interfaces.web.common.dto.ApiResponse;
import kr.hhplus.be.server.interfaces.web.product.dto.response.ProductResponse;
import kr.hhplus.be.server.interfaces.web.product.dto.response.TopProductResponse;
import kr.hhplus.be.server.interfaces.web.product.model.ProductInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "상품 관리", description = "상품 관리")
@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductFacade productFacade;

    @Operation(summary = "상품 목록 조회", description = "상품 목록을 조회한다.")
    @GetMapping
    public ApiResponse<ProductResponse> getProductList(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size,
                                                       @RequestParam(defaultValue = "productId") String sort){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort.split(",")));
        Page<ProductFacadeResponse> products = productFacade.getProducts(pageable);
        return  ApiResponse.ok(new ProductResponse(products.map(ProductInfo::toInfo)));
    }

    @Operation(summary = "상위 상품 목록 조회", description = "최근 3일간 판매량 상위 상품 5개 목록을 조회한다.")
    @GetMapping("/top")
    public ApiResponse<TopProductResponse> getTopProductList(){
        List<ProductFacadeResponse> topProducts = productFacade.getTopProducts();
        List<ProductInfo> result = topProducts.stream()
                .map(ProductInfo::toInfo)
                .collect(Collectors.toList());
        return  ApiResponse.ok(new TopProductResponse(result));
    }

}
