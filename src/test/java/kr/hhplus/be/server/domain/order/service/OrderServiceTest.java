package kr.hhplus.be.server.domain.order.service;

import kr.hhplus.be.server.domain.order.dto.request.OrderServiceRequest;
import kr.hhplus.be.server.domain.order.dto.response.OrderItem;
import kr.hhplus.be.server.domain.order.dto.response.OrderResult;
import kr.hhplus.be.server.domain.order.dto.response.TopOrderProduct;
import kr.hhplus.be.server.domain.order.entity.Order;
import kr.hhplus.be.server.domain.order.entity.OrderProduct;
import kr.hhplus.be.server.domain.order.enums.OrderStateType;
import kr.hhplus.be.server.domain.order.repository.OrderProductRepository;
import kr.hhplus.be.server.domain.order.repository.OrderRepository;
import kr.hhplus.be.server.support.exception.order.OrderErrorCode;
import kr.hhplus.be.server.support.exception.order.OrderException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderProductRepository orderProductRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    @DisplayName("유효한 주문 ID로 주문 정보를 조회한다.")
    void testFindOrderInfo_Success() {
        // Given
        Long orderId = 1L;

        Order mockOrder = new Order(orderId, 1L, 40000L, 5000L, 35000L, OrderStateType.ORDERED);
        List<OrderProduct> mockProducts = Arrays.asList(
                new OrderProduct(1L, orderId, 101L, "지코바", 1000L, 2L, 2000L),
                new OrderProduct(2L, orderId, 102L, "게코젤리", 2000L, 1L, 2000L)
        );

        when(orderRepository.findByOrderId(orderId)).thenReturn(mockOrder);
        when(orderProductRepository.findByOrderId(orderId)).thenReturn(mockProducts);

        // When
        OrderResult result = orderService.findOrderInfo(orderId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getOrderState()).isEqualTo(OrderStateType.ORDERED);
        assertThat(result.getItems()).hasSize(2);
        assertThat(result.getItems().get(0).getProductName()).isEqualTo("지코바");

        verify(orderRepository, times(1)).findByOrderId(orderId);
        verify(orderProductRepository, times(1)).findByOrderId(orderId);
    }

    @Test
    @DisplayName("유효하지 않은 주문 ID로 주문 정보를 조회할 때 예외를 발생시킨다.")
    void testFindOrderInfo_InvalidOrderId() {
        // Given
        Long orderId = 1L;

        when(orderRepository.findByOrderId(orderId)).thenReturn(null);

        // When & Then
        assertThatThrownBy(() -> orderService.findOrderInfo(orderId))
                .isInstanceOf(OrderException.class)
                .hasMessage(OrderErrorCode.INVALID_ORDER_ID.getMessage());

        verify(orderRepository, times(1)).findByOrderId(orderId);
        verify(orderProductRepository, never()).findByOrderId(anyLong());
    }

    @Test
    @DisplayName("새로운 주문을 생성한다.")
    void testCreateOrder_Success() {
        // Given
        OrderServiceRequest request = new OrderServiceRequest( 1L, 1L, 40000L, 5000L, 35000L, OrderStateType.ORDERED);
        Order mockOrder = request.toEntity();

        when(orderRepository.save(any(Order.class))).thenReturn(mockOrder);

        // When
        OrderResult result = orderService.createOrder(request);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getOrderState()).isEqualTo(OrderStateType.ORDERED);

        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    @DisplayName("주문 상품을 생성한다.")
    void testCreateOrderProduct_Success() {
        // Given
        List<OrderItem> requests = Arrays.asList(
                OrderItem.create(1L, 101L, "불고기", 1000L, 2L, 2000L),
                OrderItem.create(1L, 102L, "육개장", 2000L, 1L, 2000L)
        );

        List<OrderProduct> mockOrderProducts = Arrays.asList(
                new OrderProduct(1L, 1L, 101L, "불고기", 1000L, 2L, 2000L),
                new OrderProduct(2L, 1L, 102L, "육개장", 2000L, 1L, 2000L)
        );

        when(orderProductRepository.save(any(OrderProduct.class)))
                .thenAnswer(invocation -> invocation.getArgument(0)); // Return the same object

        // When
        List<OrderItem> result = orderService.createOrderProduct(requests);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getProductName()).isEqualTo("불고기");

        verify(orderProductRepository, times(2)).save(any(OrderProduct.class));
    }

    @Test
    @DisplayName("최근 3일간 판매 수량이 많은 상위 5개 상품을 조회한다.")
    void testFindTop5OrderProducts() {
        // Given
        List<TopOrderProduct> mockTopProducts = Arrays.asList(
                new TopOrderProduct(1L, 120L),
                new TopOrderProduct(2L, 100L),
                new TopOrderProduct(3L, 80L),
                new TopOrderProduct(4L, 60L),
                new TopOrderProduct(5L, 50L)
        );

        when(orderProductRepository.findTop5OrderProducts()).thenReturn(mockTopProducts);

        // When
        List<TopOrderProduct> result = orderService.findTop5OrderProducts();

        // Then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(5);

        assertThat(result.get(0).getProductId()).isEqualTo(1L);
        assertThat(result.get(0).getTotalQuantity()).isEqualTo(120L);

        verify(orderProductRepository, times(1)).findTop5OrderProducts();
    }

    @Test
    @DisplayName("주문 상태를 성공적으로 업데이트한다.")
    void updateOrderState_Success() {
        // Given
        Long orderId = 1L;
        OrderStateType newState = OrderStateType.PAYED;

        when(orderRepository.updateState(orderId, newState)).thenReturn(1);

        Order mockOrder = spy(Order.builder()
                .orderId(orderId)
                .orderState(OrderStateType.ORDERED)
                .build());
        doNothing().when(mockOrder).checkOrderState(newState);

        when(orderRepository.findByOrderId(orderId)).thenReturn(mockOrder);

        // When
        orderService.updateOrderState(orderId, newState);

        // Then
        verify(orderRepository, times(1)).updateState(orderId, newState);
        verify(orderRepository, times(1)).findByOrderId(orderId);

        // Verify if checkOrderState is called
        verify(mockOrder, times(1)).checkOrderState(newState);
    }

    @Test
    @DisplayName("주문 상태 업데이트 실패 시 예외를 발생시킨다.")
    void updateOrderState_Failure() {
        // Given
        Long orderId = 1L;
        OrderStateType newState = OrderStateType.PAYED;

        when(orderRepository.updateState(orderId, newState)).thenReturn(0);

        // When & Then
        assertThatThrownBy(() -> orderService.updateOrderState(orderId, newState))
                .isInstanceOf(OrderException.class)
                .hasMessage(OrderErrorCode.FAIL_UPDATE_STATUS.getMessage());

        verify(orderRepository, times(1)).updateState(orderId, newState);
        verify(orderRepository, never()).findByOrderId(orderId);
    }

}