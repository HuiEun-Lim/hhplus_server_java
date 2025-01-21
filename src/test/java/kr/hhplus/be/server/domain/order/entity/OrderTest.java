package kr.hhplus.be.server.domain.order.entity;

import kr.hhplus.be.server.domain.order.enums.OrderStateType;
import kr.hhplus.be.server.support.exception.CommonException;
import kr.hhplus.be.server.support.exception.order.OrderErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTest {

    @Test
    @DisplayName("주문 객체 생성 테스트")
    void createOrder_Success() {
        // Given
        Long userId = 1L;
        Long issuanceId = 2L;
        Long originPrice = 10000L;
        Long discountPrice = 2000L;
        Long salePrice = 8000L;
        OrderStateType orderState = OrderStateType.ORDERED;

        // When
        Order order = new Order(userId, issuanceId, originPrice, discountPrice, salePrice, orderState);

        // Then
        assertThat(order).isNotNull();
        assertThat(order.getUserId()).isEqualTo(userId);
        assertThat(order.getIssuanceId()).isEqualTo(issuanceId);
        assertThat(order.getOriginPrice()).isEqualTo(originPrice);
        assertThat(order.getDiscountPrice()).isEqualTo(discountPrice);
        assertThat(order.getSalePrice()).isEqualTo(salePrice);
        assertThat(order.getOrderState()).isEqualTo(orderState);
    }

    @Test
    @DisplayName("주문 상태 확인 성공 테스트")
    void checkOrderState_Success() {
        // Given
        Order order = Order.builder()
                .orderId(1L)
                .userId(1L)
                .issuanceId(1L)
                .originPrice(10000L)
                .discountPrice(2000L)
                .salePrice(8000L)
                .orderState(OrderStateType.ORDERED)
                .build();

        // When & Then
        order.checkOrderState(OrderStateType.ORDERED);
    }

    @Test
    @DisplayName("주문 상태 확인 실패 시 예외를 발생시킨다.")
    void checkOrderState_Failure() {
        // Given
        Order order = Order.builder()
                .orderId(1L)
                .userId(1L)
                .issuanceId(1L)
                .originPrice(10000L)
                .discountPrice(2000L)
                .salePrice(8000L)
                .orderState(OrderStateType.ORDERED)
                .build();

        // When & Then
        assertThatThrownBy(() -> order.checkOrderState(OrderStateType.PAYED))
                .isInstanceOf(CommonException.class)
                .hasMessage(OrderErrorCode.FAIL_UPDATE_STATUS.getMessage());
    }

}