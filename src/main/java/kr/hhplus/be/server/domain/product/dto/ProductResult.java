package kr.hhplus.be.server.domain.product.dto;

import kr.hhplus.be.server.domain.product.entity.Product;
import kr.hhplus.be.server.domain.product.entity.Stock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResult {
    private Long productId;
    private String productName;
    private Long price;
    private Long stock;

    public static ProductResult toResult(Product product, Stock stock){
        return ProductResult.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .stock(stock != null ? stock.getQuantity() : 0)
                .build();
    }
}
