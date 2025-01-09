package kr.hhplus.be.server.application.point.facade;

import kr.hhplus.be.server.application.point.dto.PointFacadeResponse;
import kr.hhplus.be.server.domain.point.dto.UserPointResult;
import kr.hhplus.be.server.domain.point.service.PointService;
import kr.hhplus.be.server.domain.user.dto.UserResult;
import kr.hhplus.be.server.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PointFacadeTest {

    @InjectMocks
    private PointFacade pointFacade;

    @Mock
    private PointService pointService;

    @Mock
    private UserService userService;

    @Test
    @DisplayName("사용자의 포인트를 충전한다.")
    void chargeUserPoint() {
        // given
        Long userId = 1L;
        Long amount = 100L;
        UserResult mockUserResult = UserResult.create(userId, "춘식이");
        UserPointResult mockPointResult = UserPointResult.create(userId, 200L);

        // when
        when(userService.getUserByUserId(userId)).thenReturn(mockUserResult);
        when(pointService.chargeUserPoint(userId, amount)).thenReturn(mockPointResult);
        PointFacadeResponse response = pointFacade.chargeUserPoint(userId, amount);

        // then
        assertNotNull(response);
        assertEquals(userId, response.getUserId());
        assertEquals("춘식이", response.getUserName());
        assertEquals(200L, response.getAmount());
        verify(userService, times(1)).getUserByUserId(userId);
        verify(pointService, times(1)).chargeUserPoint(userId, amount);
    }

    @Test
    @DisplayName("사용자의 포인트를 조회한다.")
    void getUserPoint() {
        // given
        Long userId = 1L;
        UserResult mockUserResult = UserResult.create(userId, "튜브");
        UserPointResult mockPointResult = UserPointResult.create(userId, 300L);

        when(userService.getUserByUserId(userId)).thenReturn(mockUserResult);
        when(pointService.getUserPoint(userId)).thenReturn(mockPointResult);

        // when
        PointFacadeResponse response = pointFacade.getUserPoint(userId);

        // then
        assertNotNull(response);
        assertEquals(userId, response.getUserId());
        assertEquals("튜브", response.getUserName());
        assertEquals(300L, response.getAmount());
        verify(userService, times(1)).getUserByUserId(userId);
        verify(pointService, times(1)).getUserPoint(userId);
    }

}