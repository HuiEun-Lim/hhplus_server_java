package kr.hhplus.be.server.domain.point.repository;

import kr.hhplus.be.server.domain.point.entity.UserPoint;
import kr.hhplus.be.server.infrastructure.db.point.impl.UserPointRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(UserPointRepositoryImpl.class)
class UserPointRepositoryTest {

    @Autowired
    private UserPointRepository userPointRepository;

    @Test
    @DisplayName("사용자의 포인트를 조회한다.")
    void findByUserId() {
        // given
        Long userId = 1L;
        Long amount = 1000L;
        UserPoint mockUserPoint = UserPoint.create(userId, amount);
        UserPoint savedUserPoint = userPointRepository.save(mockUserPoint);

        // when
        UserPoint userPoint = userPointRepository.findByUserId(userId);

        // then
        assertNotNull(userPoint);
        assertEquals(savedUserPoint.getUserId(), userPoint.getUserId());
        assertEquals(savedUserPoint.getAmount(), userPoint.getAmount());
    }

    @Test
    @DisplayName("사용자의 포인트를 Lock을 걸면서 조회한다.")
    void findByUserIdWithLock() {
        // given
        Long userId = 1L;
        Long amount = 1000L;
        UserPoint mockUserPoint = UserPoint.create(userId, amount);
        UserPoint savedUserPoint = userPointRepository.save(mockUserPoint);

        // when
        UserPoint userPoint = userPointRepository.findByUserIdWithLock(userId);

        // then
        assertNotNull(userPoint);
        assertEquals(savedUserPoint.getUserId(), userPoint.getUserId());
        assertEquals(savedUserPoint.getAmount(), userPoint.getAmount());
    }

}