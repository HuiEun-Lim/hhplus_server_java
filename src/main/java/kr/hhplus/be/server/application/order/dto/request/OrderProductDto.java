package kr.hhplus.be.server.application.order.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderProductDto {
    private Long productId;
    private Long quantity;
}
