package kr.hhplus.be.server.domain.order.repository;

import kr.hhplus.be.server.domain.order.dto.response.TopOrderProduct;
import kr.hhplus.be.server.domain.order.entity.OrderProduct;

import java.util.List;

public interface OrderProductRepository {
    List<OrderProduct> findByOrderId(Long orderId);
    OrderProduct save(OrderProduct product);
    List<TopOrderProduct> findTop5OrderProducts();
}
