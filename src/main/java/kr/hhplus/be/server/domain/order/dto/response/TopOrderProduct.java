package kr.hhplus.be.server.domain.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopOrderProduct {
    private Long productId;
    private Long totalQuantity;
}
