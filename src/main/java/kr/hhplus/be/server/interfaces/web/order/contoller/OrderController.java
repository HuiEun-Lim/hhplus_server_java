package kr.hhplus.be.server.interfaces.web.order.contoller;

import kr.hhplus.be.server.domain.order.Order;
import kr.hhplus.be.server.interfaces.web.ApiResponse;
import kr.hhplus.be.server.interfaces.web.order.dto.request.OrderRequest;
import kr.hhplus.be.server.interfaces.web.order.dto.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    @PostMapping
    public ApiResponse<OrderResponse> createOrder(@RequestBody OrderRequest request) {
        return ApiResponse.ok(new OrderResponse("주문 성공", new Order(1L, 1L, 15000L, 15000L)));
    }

}
