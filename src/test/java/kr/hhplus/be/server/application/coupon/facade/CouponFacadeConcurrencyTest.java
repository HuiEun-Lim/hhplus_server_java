package kr.hhplus.be.server.application.coupon.facade;

import kr.hhplus.be.server.application.IntegrationTestSupport;
import kr.hhplus.be.server.domain.coupon.entity.Coupon;
import kr.hhplus.be.server.domain.coupon.enums.DiscountType;
import kr.hhplus.be.server.domain.coupon.repository.CouponIssuanceRepository;
import kr.hhplus.be.server.domain.coupon.repository.CouponRepository;
import kr.hhplus.be.server.domain.user.entity.User;
import kr.hhplus.be.server.domain.user.repository.UserRepository;
import kr.hhplus.be.server.infrastructure.db.coupon.jpa.CouponIssuanceJpaRepository;
import kr.hhplus.be.server.support.exception.CommonException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

class CouponFacadeConcurrencyTest extends IntegrationTestSupport {
    @Autowired
    private CouponFacade couponFacade;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponIssuanceRepository couponIssuanceRepository;

    @Autowired
    private CouponIssuanceJpaRepository couponIssuanceJpaRepository;

    private Coupon coupon;

    private static final Logger logger = LoggerFactory.getLogger(CouponFacadeConcurrencyTest.class);

    @BeforeEach
    void setUp() {
        couponIssuanceJpaRepository.deleteAllInBatch();
        coupon = couponRepository.save(Coupon.create("1000원 할인 쿠폰", DiscountType.AMOUNT, 1000L, 1000L, 20L, LocalDateTime.now().plusDays(10)));
    }

    @Test
    @DisplayName("최대 발급 가능한 쿠폰의 수의 사용자가 쿠폰을 발급한다.")
    void issueCouponMaxConcurrency() throws InterruptedException {
        // given
        int usersCount = 20;
        long couponId = coupon.getCouponId();

        // when
        ExecutorService executorService = Executors.newFixedThreadPool(usersCount);
        CountDownLatch latch = new CountDownLatch(usersCount);

        for (int i = 1; i <= usersCount; i++) {
            User user = userRepository.save(User.create(String.valueOf(i)));
            executorService.execute(() -> {
                try {
                    couponFacade.issueCoupon(user.getUserId(), couponId);
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
        Long issuanceCount = couponIssuanceRepository.countByCouponId(couponId);
        assertThat(issuanceCount).isEqualTo(coupon.getMaxIssuanceCount());

    }

    @Test
    @DisplayName("한 사용자가 같은 쿠폰을 5번 발급한다.")
    void issueCouponOneUserFiveTimes() throws InterruptedException {
        // given
        int processCount = 5;
        User user = userRepository.save(User.create("히으니"));
        long couponId = coupon.getCouponId();

        // when
        ExecutorService executorService = Executors.newFixedThreadPool(processCount);
        CountDownLatch latch = new CountDownLatch(processCount);

        for (int i = 0; i < processCount; i++) {
            executorService.execute(() -> {
                try {
                    couponFacade.issueCoupon(user.getUserId(), couponId);
                } catch (CommonException e) {
                    logger.error(e.getMessage(), e);
                } finally {
                    latch.countDown();
                }
            });

        }

        latch.await();
        executorService.shutdown();

        int issuanceCount = couponIssuanceJpaRepository.countByUserIdAndCouponId(user.getUserId(), couponId);
        assertThat(issuanceCount).isEqualTo(1);
    }
}