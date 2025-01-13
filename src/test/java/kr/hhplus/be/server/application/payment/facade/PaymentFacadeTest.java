package kr.hhplus.be.server.application.payment.facade;

import kr.hhplus.be.server.application.payment.dto.request.PaymnetFacadeRequset;
import kr.hhplus.be.server.application.payment.dto.response.PaymentFacadeResponse;
import kr.hhplus.be.server.domain.order.dto.response.OrderResult;
import kr.hhplus.be.server.domain.order.enums.OrderStateType;
import kr.hhplus.be.server.domain.order.service.OrderService;
import kr.hhplus.be.server.domain.payment.dto.PaymentResult;
import kr.hhplus.be.server.domain.payment.dto.PaymentServiceRequest;
import kr.hhplus.be.server.domain.payment.enums.PaymentStatusType;
import kr.hhplus.be.server.domain.payment.service.PaymentService;
import kr.hhplus.be.server.support.exception.payment.PaymentErrorCode;
import kr.hhplus.be.server.support.exception.payment.PaymentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentFacadeTest {

    @InjectMocks
    private PaymentFacade paymentFacade;

    @Mock
    private PaymentService paymentService;

    @Mock
    private OrderService orderService;

    @Test
    @DisplayName("결제를 성공적으로 생성한다.")
    void createPayment_Success() {
        // Given
        Long orderId = 1L;
        Long salePrice = 5000L;

        OrderResult mockOrder = OrderResult.builder()
                .orderId(orderId)
                .salePrice(salePrice)
                .orderState(OrderStateType.ORDERED)
                .build();
        when(orderService.findOrderInfoNoProduct(orderId)).thenReturn(mockOrder);

        PaymentResult mockPaymentResult = PaymentResult.builder()
                .paymentId(100L)
                .orderId(orderId)
                .payAmt(salePrice)
                .paymentStatus(PaymentStatusType.PAYED)
                .build();
        when(paymentService.createPayment(any(PaymentServiceRequest.class))).thenReturn(mockPaymentResult);

        PaymnetFacadeRequset request = PaymnetFacadeRequset.builder()
                .orderId(orderId)
                .build();

        // When
        PaymentFacadeResponse response = paymentFacade.createPayment(request);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getPaymentId()).isEqualTo(100L);
        assertThat(response.getOrderId()).isEqualTo(orderId);
        assertThat(response.getPayAmt()).isEqualTo(salePrice);
        assertThat(response.getPaymentStatus()).isEqualTo(PaymentStatusType.PAYED);

        verify(orderService, times(1)).findOrderInfoNoProduct(orderId);
        verify(paymentService, times(1)).createPayment(any(PaymentServiceRequest.class));
        verify(orderService, times(1)).updateOrderState(orderId, OrderStateType.PAYED);
    }

    @Test
    @DisplayName("이미 결제된 주문에 대해 예외를 발생시킨다.")
    void createPayment_Failure_AlreadyPayed() {
        // Given
        Long orderId = 1L;

        OrderResult mockOrder = OrderResult.builder()
                .orderId(orderId)
                .salePrice(5000L)
                .orderState(OrderStateType.PAYED)
                .build();
        when(orderService.findOrderInfoNoProduct(orderId)).thenReturn(mockOrder);

        PaymnetFacadeRequset request = PaymnetFacadeRequset.builder()
                .orderId(orderId)
                .build();

        // When & Then
        assertThatThrownBy(() -> paymentFacade.createPayment(request))
                .isInstanceOf(PaymentException.class)
                .hasMessage(PaymentErrorCode.INVALID_ORDER_STATE.getMessage());

        verify(orderService, times(1)).findOrderInfoNoProduct(orderId);
        verify(paymentService, never()).createPayment(any(PaymentServiceRequest.class));
        verify(orderService, never()).updateOrderState(anyLong(), any(OrderStateType.class));
    }
}