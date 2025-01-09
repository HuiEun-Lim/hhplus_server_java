package kr.hhplus.be.server.interfaces.web.order.dto.request;

import kr.hhplus.be.server.domain.product.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderRequest {

    @NotNull
    private Long userId;

    @NotNull
    private List<Product> orderItems;

    private Long couponId;
}
