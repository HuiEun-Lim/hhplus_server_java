package kr.hhplus.be.server.interfaces.web.order.contoller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.domain.order.entity.Order;
import kr.hhplus.be.server.domain.order.enums.OrderStateType;
import kr.hhplus.be.server.interfaces.web.ApiResponse;
import kr.hhplus.be.server.interfaces.web.order.dto.request.OrderRequest;
import kr.hhplus.be.server.interfaces.web.order.dto.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "주문 관리", description = "주문 관리 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Operation(summary = "주문 생성", description = "주문 정보를 생성한다.")
    @PostMapping
    public ApiResponse<OrderResponse> createOrder(@RequestBody OrderRequest request) {
        return ApiResponse.ok(new OrderResponse("주문 성공", new Order(1L, null, 15000L, 0L, 15000L, OrderStateType.ORDERED)));
    }

}
