package kr.hhplus.be.server.application.order.facade;

import kr.hhplus.be.server.application.IntegrationTestSupport;
import kr.hhplus.be.server.application.order.dto.request.OrderFacadeRequest;
import kr.hhplus.be.server.application.order.dto.request.OrderProductDto;
import kr.hhplus.be.server.domain.product.entity.Product;
import kr.hhplus.be.server.domain.product.entity.Stock;
import kr.hhplus.be.server.domain.user.entity.User;
import kr.hhplus.be.server.infrastructure.db.product.jpa.ProductJpaRepository;
import kr.hhplus.be.server.infrastructure.db.product.jpa.StockJpaRepository;
import kr.hhplus.be.server.support.exception.CommonException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class OrderFacadeConcurrencyTest extends IntegrationTestSupport {

    @Autowired
    private OrderFacade orderFacade;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private StockJpaRepository stockJpaRepository;

    private static final Logger logger = LoggerFactory.getLogger(OrderFacadeConcurrencyTest.class);

    @Test
    @DisplayName("동시에 5명의 사용자가 하나의 상품을 주문한다.")
    void orderFiveUserOneProduct() throws InterruptedException {
        // given
        int usersCount = 5;
        Product product = productJpaRepository.save(Product.create("재고 테스트 상품", 5000L));
        stockJpaRepository.save(Stock.create(product.getProductId(), 10L));

        List<OrderProductDto> products = List.of(new OrderProductDto(product.getProductId(), 1L));

        // when
        ExecutorService executorService = Executors.newFixedThreadPool(usersCount);
        CountDownLatch latch = new CountDownLatch(usersCount);

        for (int i = 1; i <= usersCount; i++) {
            User user = userRepository.save(User.create(String.valueOf(i)));
            OrderFacadeRequest request = OrderFacadeRequest.create(user.getUserId(), null, products);
            executorService.execute(() -> {
                try {
                    orderFacade.createOrder(request);
                } catch (CommonException e) {
                    logger.error(e.getMessage(), e);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        // then
        Stock stock = stockJpaRepository.findByProductId(product.getProductId());
        assertThat(stock.getQuantity()).isEqualTo(5L);


    }




}