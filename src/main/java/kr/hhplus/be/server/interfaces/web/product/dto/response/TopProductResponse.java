package kr.hhplus.be.server.interfaces.web.product.dto.response;

import kr.hhplus.be.server.interfaces.web.product.model.ProductInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopProductResponse {
    private List<ProductInfo> topProducts;
}
