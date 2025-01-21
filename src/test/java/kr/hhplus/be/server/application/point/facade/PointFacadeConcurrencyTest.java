package kr.hhplus.be.server.application.point.facade;

import kr.hhplus.be.server.application.IntegrationTestSupport;
import kr.hhplus.be.server.domain.point.entity.UserPoint;
import kr.hhplus.be.server.domain.user.entity.User;
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

class PointFacadeConcurrencyTest extends IntegrationTestSupport {

    @Autowired
    private PointFacade pointFacade;

    @Autowired
    private UserPointJpaRepository userPointJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    private static final Logger logger = LoggerFactory.getLogger(PointFacadeConcurrencyTest.class);

    @Test
    @DisplayName("한 사용자가 3번 포인트 충전을 한다.")
    void chargePointThreeTimes() throws InterruptedException {
        // given
        int processCount = 3;
        User user = userJpaRepository.save(User.create("이미은"));
        Long userId = user.getUserId();
        Long amount = 5000L;

        userPointJpaRepository.save(UserPoint.create(userId, 0L));

        // when
        ExecutorService executorService = Executors.newFixedThreadPool(processCount);
        CountDownLatch latch = new CountDownLatch(processCount);

        for (int i = 0; i < processCount; i++) {
            executorService.execute(() -> {
                try {
                    pointFacade.chargeUserPoint(userId, amount);
                } catch (CommonException e) {
                    logger.error(e.getMessage(), e);
                } finally {
                    latch.countDown();
                }
            });

        }

        latch.await();
        executorService.shutdown();

        UserPoint finalUserPoint = userPointJpaRepository.findByUserId(userId);
        assertThat(finalUserPoint.getAmount()).isEqualTo(5000L);
    }
}