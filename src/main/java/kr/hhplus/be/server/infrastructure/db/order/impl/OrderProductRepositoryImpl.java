package kr.hhplus.be.server.infrastructure.db.order.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.hhplus.be.server.domain.order.dto.response.TopOrderProduct;
import kr.hhplus.be.server.domain.order.entity.OrderProduct;
import kr.hhplus.be.server.domain.order.entity.QOrderProduct;
import kr.hhplus.be.server.domain.order.repository.OrderProductRepository;
import kr.hhplus.be.server.infrastructure.db.order.jpa.OrderProductJpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class OrderProductRepositoryImpl implements OrderProductRepository {

    private final OrderProductJpaRepository orderProductJpaRepository;
    private final JPAQueryFactory queryFactory;

    public OrderProductRepositoryImpl(OrderProductJpaRepository orderProductJpaRepository, JPAQueryFactory queryFactory) {
        this.orderProductJpaRepository = orderProductJpaRepository;
        this.queryFactory = queryFactory;
    }

    @Override
    public List<OrderProduct> findByOrderId(Long orderId) {
        return orderProductJpaRepository.findByOrderId(orderId);
    }

    @Override
    public OrderProduct save(OrderProduct product) {
        return orderProductJpaRepository.save(product);
    }

    @Override
    public List<TopOrderProduct> findTop5OrderProducts() {
        QOrderProduct orderProduct = QOrderProduct.orderProduct;

        LocalDateTime threeDaysAgo = LocalDateTime.now().minusDays(3);

        return queryFactory.select(Projections.constructor(
                        TopOrderProduct.class,
                        orderProduct.productId,
                        orderProduct.quantity.sum().as("totalQuantity")
                ))
                .from(orderProduct)
                .where(orderProduct.createdAt.after(threeDaysAgo))
                .groupBy(orderProduct.productId)
                .orderBy(orderProduct.quantity.sum().desc())
                .limit(5)
                .fetch();
    }
}
