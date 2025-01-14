package kr.hhplus.be.server.application.product.dto;

import kr.hhplus.be.server.domain.product.dto.ProductResult;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductFacadeResponse {
    private Long productId;
    private String productName;
    private Long price;
    private Long stock;

    public static ProductFacadeResponse toResponse(ProductResult result){
        return ProductFacadeResponse.builder()
                .productId(result.getProductId())
                .productName(result.getProductName())
                .price(result.getPrice())
                .stock(result.getStock())
                .build();
    }
}
