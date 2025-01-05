package kr.hhplus.be.server.interfaces.payment.controller;

import kr.hhplus.be.server.domain.order.OrderPay;
import kr.hhplus.be.server.interfaces.ApiResponse;
import kr.hhplus.be.server.interfaces.payment.dto.request.PaymentRequest;
import kr.hhplus.be.server.interfaces.payment.dto.response.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payments")
public class PaymentController {

    @PostMapping
    public ApiResponse<PaymentResponse> payOrder(@RequestBody PaymentRequest request) {
        return ApiResponse.ok(new PaymentResponse("결제 성공", new OrderPay(1L, 1L, 15000L)));
    }
}
