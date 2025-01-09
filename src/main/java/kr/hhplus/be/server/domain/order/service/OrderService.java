package kr.hhplus.be.server.domain.order.service;

import kr.hhplus.be.server.domain.order.dto.request.OrderProductServiceRequest;
import kr.hhplus.be.server.domain.order.dto.request.OrderServiceRequest;
import kr.hhplus.be.server.domain.order.dto.response.OrderItem;
import kr.hhplus.be.server.domain.order.dto.response.OrderResult;
import kr.hhplus.be.server.domain.order.entity.Order;
import kr.hhplus.be.server.domain.order.entity.OrderProduct;
import kr.hhplus.be.server.domain.order.repository.OrderProductRepository;
import kr.hhplus.be.server.domain.order.repository.OrderRepository;
import kr.hhplus.be.server.support.exception.order.OrderErrorCode;
import kr.hhplus.be.server.support.exception.order.OrderException;
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
            throw new OrderException(OrderErrorCode.INVALID_ORDER_ID);
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
    public List<OrderItem> createOrderProduct(List<OrderProductServiceRequest> requests) {
        List<OrderItem> result = new ArrayList<>();
        for(OrderProductServiceRequest request : requests) {
            OrderProduct item = orderProductRepository.save(request.toEntity());
            result.add(OrderItem.toItem(item));
        }

        return result;
    }
}
