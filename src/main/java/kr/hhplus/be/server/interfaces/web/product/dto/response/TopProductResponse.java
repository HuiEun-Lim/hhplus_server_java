package kr.hhplus.be.server.interfaces.web.product.dto.response;

import kr.hhplus.be.server.domain.product.entity.Product;
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
    private String message;
    private List<Product> data;
}
