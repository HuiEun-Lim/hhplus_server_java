package kr.hhplus.be.server.application.point.facade;

import kr.hhplus.be.server.application.point.dto.PointFacadeResponse;
import kr.hhplus.be.server.domain.point.entity.UserPoint;
import kr.hhplus.be.server.domain.point.repository.UserPointRepository;
import kr.hhplus.be.server.domain.user.entity.User;
import kr.hhplus.be.server.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PointFacadeIntegrationTest {
    @Autowired
    private PointFacade pointFacade;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPointRepository pointRepository;

    @Test
    @DisplayName("사용자의 포인트를 성공적으로 충전한다.")
    void chargeUserPoint() {
        // Given
        User user = userRepository.save(User.create("이호민"));
        Long userId = user.getUserId();
        Long chargeAmount = 1000L;

        // When
        PointFacadeResponse response = pointFacade.chargeUserPoint(userId, chargeAmount);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getUserId()).isEqualTo(userId);
        assertThat(response.getUserName()).isEqualTo("이호민");
        assertThat(response.getAmount()).isEqualTo(chargeAmount);
    }

    @Test
    @DisplayName("사용자의 현재 포인트를 조회한다.")
    void getUserPoint() {
        // Given
        User user = userRepository.save(User.create("최재영"));
        Long userId = user.getUserId();

        pointRepository.save(UserPoint.create(userId, 5000L));

        // When
        PointFacadeResponse response = pointFacade.getUserPoint(userId);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getUserId()).isEqualTo(userId);
        assertThat(response.getUserName()).isEqualTo("최재영");
        assertThat(response.getAmount()).isEqualTo(5000L);
    }
}