package kr.hhplus.be.server.application.product.facade;

import kr.hhplus.be.server.application.product.dto.ProductFacadeResponse;
import kr.hhplus.be.server.domain.order.dto.response.TopOrderProduct;
import kr.hhplus.be.server.domain.order.service.OrderService;
import kr.hhplus.be.server.domain.product.dto.ProductResult;
import kr.hhplus.be.server.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component("productFacade")
@RequiredArgsConstructor
public class ProductFacade {

    private final ProductService productService;

    private final OrderService orderService;

    @Transactional(readOnly = true)
    public Page<ProductFacadeResponse> getProducts(Pageable pageable){
        Page<ProductResult> results = productService.getProductList(pageable);

        return results.map(ProductFacadeResponse::toResponse);
    }

    @Transactional(readOnly = true)
    public List<ProductFacadeResponse> getTopProducts() {
        List<TopOrderProduct> topList = orderService.findTop5OrderProducts();

        List<ProductFacadeResponse> responses = new ArrayList<>();

        for(TopOrderProduct topProduct : topList){
            ProductResult product = productService.getProductByProductId(topProduct.getProductId());
            responses.add(ProductFacadeResponse.toResponse(product));
        }

        return responses;
    }
}
