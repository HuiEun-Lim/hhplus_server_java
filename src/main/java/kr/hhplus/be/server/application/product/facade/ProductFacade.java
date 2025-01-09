package kr.hhplus.be.server.application.product.facade;

import kr.hhplus.be.server.application.product.dto.ProductFacadeResponse;
import kr.hhplus.be.server.domain.product.dto.ProductResult;
import kr.hhplus.be.server.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component("productFacade")
@RequiredArgsConstructor
public class ProductFacade {

    private final ProductService productService;

    public Page<ProductFacadeResponse> getProducts(Pageable pageable){
        Page<ProductResult> results = productService.getProductList(pageable);

        return results.map(ProductFacadeResponse::toResponse);
    }
}
