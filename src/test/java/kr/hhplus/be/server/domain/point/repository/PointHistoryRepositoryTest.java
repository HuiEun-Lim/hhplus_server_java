package kr.hhplus.be.server.domain.point.repository;

import kr.hhplus.be.server.domain.point.entity.PointHistory;
import kr.hhplus.be.server.domain.point.enums.TransactionType;
import kr.hhplus.be.server.infrastructure.db.point.impl.PointHistoryRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PointHistoryRepositoryImpl.class)
class PointHistoryRepositoryTest {

    @Autowired
    private PointHistoryRepository pointHistoryRepository;

    @Test
    @DisplayName("사용자의 아이디로 포인트 히스토리 목록을 조회한다.")
    void findByUserId() {
        // given
        Long userId = 1L;

        PointHistory history1 = PointHistory.create(userId, 1000L, TransactionType.CHARGE);
        PointHistory history2 = PointHistory.create(userId, 2000L, TransactionType.CHARGE);

        pointHistoryRepository.save(history1);
        pointHistoryRepository.save(history2);

        // when
        List<PointHistory> list = pointHistoryRepository.findByUserId(userId);

        // then
        assertNotNull(list);
        assertEquals(list.get(0).getAmount(), history1.getAmount());
        assertEquals(list.get(0).getTransactionType(), history1.getTransactionType());
        assertEquals(list.get(1).getAmount(), history2.getAmount());
        assertEquals(list.get(1).getTransactionType(), history2.getTransactionType());

    }

}