package kr.hhplus.be.server.application.payment.facade;

import kr.hhplus.be.server.application.IntegrationTestSupport;
import kr.hhplus.be.server.application.payment.dto.request.PaymnetFacadeRequset;
import kr.hhplus.be.server.application.payment.dto.response.PaymentFacadeResponse;
import kr.hhplus.be.server.domain.order.entity.Order;
import kr.hhplus.be.server.domain.order.enums.OrderStateType;
import kr.hhplus.be.server.domain.order.repository.OrderRepository;
import kr.hhplus.be.server.domain.payment.enums.PaymentStatusType;
import kr.hhplus.be.server.domain.user.entity.User;
import kr.hhplus.be.server.support.exception.CommonException;
import kr.hhplus.be.server.support.exception.payment.PaymentErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PaymentFacadeIntegrationTest extends IntegrationTestSupport {

    @Autowired
    private PaymentFacade paymentFacade;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("주문 결제를 성공적으로 생성한다.")
    void createPaymentSuccessfully() {
        // Given
        User user = userRepository.save(User.create("임흐이은"));

        Order order = orderRepository.save(Order.create(
                user.getUserId(), null, 13000L, 0L, 13000L, OrderStateType.ORDERED));

        PaymnetFacadeRequset request = new PaymnetFacadeRequset(user.getUserId(), order.getOrderId());

        // When
        PaymentFacadeResponse response = paymentFacade.createPayment(request);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getOrderId()).isEqualTo(order.getOrderId());
        assertThat(response.getPayAmt()).isEqualTo(order.getSalePrice());
        assertThat(response.getPaymentStatus()).isEqualTo(PaymentStatusType.PAYED);
    }

    @Test
    @DisplayName("이미 결제된 주문에 대해 결제 요청 시 예외를 발생시킨다.")
    void throwExceptionForAlreadyPaidOrder() {
        // Given
        User user = userRepository.save(User.create("테스트 사용자"));

        Order order = orderRepository.save(Order.create(
                user.getUserId(), null, 13000L, 0L, 13000L, OrderStateType.PAYED));

        PaymnetFacadeRequset request = new PaymnetFacadeRequset(user.getUserId(), order.getOrderId());

        // When & Then
        assertThatThrownBy(() -> paymentFacade.createPayment(request))
                .isInstanceOf(CommonException.class)
                .hasMessage(PaymentErrorCode.INVALID_ORDER_STATE.getMessage());
    }
}