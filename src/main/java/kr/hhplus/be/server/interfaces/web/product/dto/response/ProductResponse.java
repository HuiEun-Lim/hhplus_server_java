package kr.hhplus.be.server.interfaces.web.product.dto.response;

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
    private Page<ProductInfo> products;
}
