package kr.hhplus.be.server.interfaces.web.payment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.domain.payment.entity.Payment;
import kr.hhplus.be.server.domain.payment.enums.PaymentStatusType;
import kr.hhplus.be.server.interfaces.web.ApiResponse;
import kr.hhplus.be.server.interfaces.web.payment.dto.request.PaymentRequest;
import kr.hhplus.be.server.interfaces.web.payment.dto.response.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "결제 관리", description = "결제 관리 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Operation(summary = "결제 생성", description = "주문에 대한 결제 정보를 생성한다.")
    @PostMapping
    public ApiResponse<PaymentResponse> payOrder(@RequestBody PaymentRequest request) {
        return ApiResponse.ok(new PaymentResponse("결제 성공", new Payment(1L, 1L, 15000L, PaymentStatusType.PAYED)));
    }
}
