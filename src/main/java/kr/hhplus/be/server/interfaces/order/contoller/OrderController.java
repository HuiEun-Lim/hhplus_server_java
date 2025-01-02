package kr.hhplus.be.server.interfaces.order.contoller;

import kr.hhplus.be.server.domain.order.Order;
import kr.hhplus.be.server.domain.order.OrderPay;
import kr.hhplus.be.server.interfaces.ApiResponse;
import kr.hhplus.be.server.interfaces.order.dto.request.OrderPayRequest;
import kr.hhplus.be.server.interfaces.order.dto.request.OrderRequest;
import kr.hhplus.be.server.interfaces.order.dto.response.OrderPayResponse;
import kr.hhplus.be.server.interfaces.order.dto.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController {

    @PostMapping
    public ApiResponse<OrderResponse> createOrder(@RequestBody OrderRequest request) {
        return ApiResponse.ok(new OrderResponse("주문 성공", new Order(1L, 1L, 15000L, 15000L)));
    }

    @PostMapping("/pay")
    public ApiResponse<OrderPayResponse> payOrder(@RequestBody OrderPayRequest request) {
        return ApiResponse.ok(new OrderPayResponse("결제 성공", new OrderPay(1L, 1L, 15000L)));
    }
}
