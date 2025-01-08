package kr.hhplus.be.server.domain.point.service;

import kr.hhplus.be.server.domain.point.dto.UserPointResult;
import kr.hhplus.be.server.domain.point.entity.PointHistory;
import kr.hhplus.be.server.domain.point.entity.UserPoint;
import kr.hhplus.be.server.domain.point.repository.PointHistoryRepository;
import kr.hhplus.be.server.domain.point.repository.UserPointRepository;
import kr.hhplus.be.server.support.exception.point.PointErrorCode;
import kr.hhplus.be.server.support.exception.point.PointException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PointServiceTest {

    @Mock
    private UserPointRepository userPointRepository;

    @Mock
    private PointHistoryRepository pointHistoryRepository;

    @InjectMocks
    private PointService pointService;

    @Test
    @DisplayName("기존 사용자의 포인트를 충전한다.")
    void chargeUserPoint() {
        // given
        Long userId = 1L;
        Long amount = 1000L;
        UserPoint existingUserPoint = UserPoint.create(userId, 500L);

        // when
        when(userPointRepository.findByUserIdWithLock(userId)).thenReturn(existingUserPoint);
        when(userPointRepository.save(any(UserPoint.class))).thenReturn(existingUserPoint);
        when(pointHistoryRepository.save(any(PointHistory.class))).thenReturn(mock(PointHistory.class));
        UserPointResult result = pointService.chargeUserPoint(userId, amount);

        // then
        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals(1500L, result.getAmount());
        verify(userPointRepository, times(1)).findByUserIdWithLock(userId);
        verify(userPointRepository, times(1)).save(existingUserPoint);
        verify(pointHistoryRepository, times(1)).save(any(PointHistory.class));
    }

    @Test
    @DisplayName("새로운 사용자의 포인트를 충전한다.")
    void chargeNewUserPoint() {
        // given
        Long userId = 2L;
        Long amount = 500L;

        // When
        when(userPointRepository.findByUserIdWithLock(userId)).thenReturn(null);
        when(userPointRepository.save(any(UserPoint.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(pointHistoryRepository.save(any(PointHistory.class))).thenReturn(mock(PointHistory.class));

        UserPointResult result = pointService.chargeUserPoint(userId, amount);

        // Then
        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals(500L, result.getAmount());
        verify(userPointRepository, times(1)).findByUserIdWithLock(userId);
        verify(userPointRepository, times(1)).save(any(UserPoint.class));
        verify(pointHistoryRepository, times(1)).save(any(PointHistory.class));
    }

    @Test
    @DisplayName("사용자의 포인트를 조회한다.")
    void getUserPoint() {
        // given
        Long userId = 1L;
        UserPoint existingUserPoint = UserPoint.create(userId, 1000L);

        // when
        when(userPointRepository.findByUserId(userId)).thenReturn(existingUserPoint);
        UserPointResult result = pointService.getUserPoint(userId);

        // then
        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals(1000L, result.getAmount());
        verify(userPointRepository, times(1)).findByUserId(userId);
    }

}