package kr.hhplus.be.server.application.payment.facade;

import kr.hhplus.be.server.application.IntegrationTestSupport;
import kr.hhplus.be.server.application.payment.dto.request.PaymnetFacadeRequset;
import kr.hhplus.be.server.domain.order.entity.Order;
import kr.hhplus.be.server.domain.order.enums.OrderStateType;
import kr.hhplus.be.server.domain.payment.entity.Payment;
import kr.hhplus.be.server.domain.point.entity.UserPoint;
import kr.hhplus.be.server.domain.user.entity.User;
import kr.hhplus.be.server.infrastructure.db.order.jpa.OrderJpaRepository;
import kr.hhplus.be.server.infrastructure.db.payment.jpa.PaymentJpaRepository;
import kr.hhplus.be.server.infrastructure.db.point.jpa.UserPointJpaRepository;
import kr.hhplus.be.server.infrastructure.db.user.jpa.UserJpaRepository;
import kr.hhplus.be.server.support.exception.CommonException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentFacadeConcurrencyTest extends IntegrationTestSupport {

    @Autowired
    private PaymentFacade paymentFacade;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private OrderJpaRepository orderJpaRepository;

    @Autowired
    private PaymentJpaRepository paymentJpaRepository;

    @Autowired
    private UserPointJpaRepository userPointJpaRepository;

    private static final Logger logger = LoggerFactory.getLogger(PaymentFacadeConcurrencyTest.class);

    @Test
    @DisplayName("한 사용자가 한 주문에 대하여 여러번 결제를 한다.")
    void paymentFacadeConcurrencyTest() throws InterruptedException {
        // given
        int processCount = 3;
        User user = userJpaRepository.save(User.create("늘 밤새는 임희은"));
        Order order = orderJpaRepository.save(Order.create(user.getUserId(), null, 5000L, 0L, 5000L, OrderStateType.ORDERED));
        Long userId = user.getUserId();
        Long orderId = order.getOrderId();
        userPointJpaRepository.save(UserPoint.create(userId, 6000L));

        PaymnetFacadeRequset request = new PaymnetFacadeRequset(userId, orderId);

        // when
        ExecutorService executorService = Executors.newFixedThreadPool(processCount);
        CountDownLatch latch = new CountDownLatch(processCount);

        for (int i = 0; i < processCount; i++) {
            executorService.execute(() -> {
                try {
                    paymentFacade.createPayment(request);
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
        int paymentCount = paymentJpaRepository.countByOrderId(orderId);
        assertThat(paymentCount).isEqualTo(1);
        UserPoint userPoint = userPointJpaRepository.findByUserId(userId);
        assertThat(userPoint.getAmount()).isEqualTo(1000L);


    }

}