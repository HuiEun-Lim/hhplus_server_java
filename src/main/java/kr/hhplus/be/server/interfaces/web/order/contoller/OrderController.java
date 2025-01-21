package kr.hhplus.be.server.interfaces.web.order.contoller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.application.order.dto.response.OrderFacadeResponse;
import kr.hhplus.be.server.application.order.facade.OrderFacade;
import kr.hhplus.be.server.interfaces.web.common.dto.ApiResponse;
import kr.hhplus.be.server.interfaces.web.order.dto.request.OrderRequest;
import kr.hhplus.be.server.interfaces.web.order.dto.response.OrderResponse;
import kr.hhplus.be.server.interfaces.web.order.model.OrderInfo;
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

    private final OrderFacade orderFacade;

    @Operation(summary = "주문 생성", description = "주문 정보를 생성한다.")
    @PostMapping
    public ApiResponse<OrderResponse> createOrder(@RequestBody OrderRequest request) {
        OrderFacadeResponse result = orderFacade.createOrder(request.toOrderFacadeRequest());
        return ApiResponse.ok(new OrderResponse(OrderInfo.toInfo(result)));
    }

}
