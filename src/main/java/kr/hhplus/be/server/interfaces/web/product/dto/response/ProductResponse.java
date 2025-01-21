package kr.hhplus.be.server.interfaces.web.product.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hhplus.be.server.interfaces.web.product.model.ProductInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    @Schema(description = "상품 목록")
    private Page<ProductInfo> products;
}
