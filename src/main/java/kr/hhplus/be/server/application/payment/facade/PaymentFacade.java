package kr.hhplus.be.server.application.payment.facade;

import kr.hhplus.be.server.application.payment.dto.request.PaymnetFacadeRequset;
import kr.hhplus.be.server.application.payment.dto.response.PaymentFacadeResponse;
import kr.hhplus.be.server.domain.order.dto.response.OrderResult;
import kr.hhplus.be.server.domain.order.enums.OrderStateType;
import kr.hhplus.be.server.domain.order.service.OrderService;
import kr.hhplus.be.server.domain.payment.dto.PaymentResult;
import kr.hhplus.be.server.domain.payment.enums.PaymentStatusType;
import kr.hhplus.be.server.domain.payment.service.PaymentService;
import kr.hhplus.be.server.support.exception.payment.PaymentErrorCode;
import kr.hhplus.be.server.support.exception.payment.PaymentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("paymentFacade")
@RequiredArgsConstructor
public class PaymentFacade {

    private final PaymentService paymentService;

    private final OrderService orderService;

    @Transactional
    public PaymentFacadeResponse createPayment(PaymnetFacadeRequset request) {
        Long orderId = request.getOrderId();
        OrderResult order = orderService.findOrderInfoNoProduct(orderId);

        if(OrderStateType.PAYED.equals(order.getOrderState())) {
            throw new PaymentException(PaymentErrorCode.INVALID_ORDER_STATE);
        }

        PaymentResult paymentResult = paymentService.createPayment(request.toServiceRequest(order.getSalePrice(), PaymentStatusType.PAYED));

        orderService.updateOrderState(orderId, OrderStateType.PAYED);

        return PaymentFacadeResponse.toResponse(paymentResult);
    }


}
