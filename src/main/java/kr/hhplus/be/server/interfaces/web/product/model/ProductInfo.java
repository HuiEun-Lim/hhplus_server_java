package kr.hhplus.be.server.interfaces.web.product.model;

import kr.hhplus.be.server.application.product.dto.ProductFacadeResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductInfo {
    private Long productId;
    private String productName;
    private Long price;
    private Long stock;

    public static ProductInfo toInfo(ProductFacadeResponse response){
        return ProductInfo.builder()
                .productId(response.getProductId())
                .productName(response.getProductName())
                .price(response.getPrice())
                .stock(response.getStock())
                .build();
    }
}
