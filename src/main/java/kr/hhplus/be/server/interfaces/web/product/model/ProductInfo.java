package kr.hhplus.be.server.interfaces.web.product.model;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hhplus.be.server.application.product.dto.ProductFacadeResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductInfo {
    @Schema(description = "상품 ID")
    private Long productId;

    @Schema(description = "상품명")
    private String productName;

    @Schema(description = "가격")
    private Long price;

    @Schema(description = "재고")
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
