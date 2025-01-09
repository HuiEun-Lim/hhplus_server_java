package kr.hhplus.be.server.interfaces.web.product.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Getter
@NoArgsConstructor
public class ProductRequest {
    private Pageable pageable;
}
