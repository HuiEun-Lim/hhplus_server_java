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
import kr.hhplus.be.server.support.exception.CommonException;
import kr.hhplus.be.server.support.exception.order.OrderErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderProductRepository orderProductRepository;

    @Transactional(readOnly = true)
    public OrderResult findOrderInfo(Long orderId) {
        Order order = orderRepository.findByOrderId(orderId);
        if (order == null) {
            throw new CommonException(OrderErrorCode.INVALID_ORDER_ID);
        }

        List<OrderProduct> productList = orderProductRepository.findByOrderId(orderId);

        OrderResult result = OrderResult.toResult(order);
        List<OrderItem> items = productList.stream()
                .map(OrderItem::toItem).toList();
        result.setItems(items);

        return result;
    }

    @Transactional
    public OrderResult createOrder(OrderServiceRequest request) {
        Order order = orderRepository.save(request.toEntity());
        return OrderResult.toResult(order);
    }

    @Transactional
    public List<OrderItem> createOrderProduct(List<OrderItem> request) {
        List<OrderItem> result = new ArrayList<>();
        for(OrderItem ordItem : request) {
            OrderProduct item = orderProductRepository.save(ordItem.toEntity());
            result.add(OrderItem.toItem(item));
        }

        return result;
    }

    @Transactional(readOnly = true)
    public List<TopOrderProduct> findTop5OrderProducts() {
        return orderProductRepository.findTop5OrderProducts();
    }

    @Transactional(readOnly = true)
    public OrderResult findOrderInfoNoProduct(Long orderId) {
        Order order = orderRepository.findByOrderId(orderId);
        if (order == null) {
            throw new CommonException(OrderErrorCode.INVALID_ORDER_ID);
        }

        return OrderResult.toResult(order);
    }

    @Transactional
    public void updateOrderState(Long orderId, OrderStateType stateType) {
        if(orderRepository.updateState(orderId, stateType) < 1) {
            throw new CommonException(OrderErrorCode.FAIL_UPDATE_STATUS);
        }
    }
}
