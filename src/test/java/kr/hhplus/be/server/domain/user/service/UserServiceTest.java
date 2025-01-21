package kr.hhplus.be.server.domain.user.service;

import kr.hhplus.be.server.domain.user.dto.UserResult;
import kr.hhplus.be.server.domain.user.entity.User;
import kr.hhplus.be.server.domain.user.repository.UserRepository;
import kr.hhplus.be.server.support.exception.CommonException;
import kr.hhplus.be.server.support.exception.user.UserErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("존재하지 않은 사용자를 조회한다.")
    void getInvalidUserByUserId() {
        // Given
        Long userId = 1L;
        when(userRepository.findByUserId(userId)).thenReturn(null);

        // When & Then
        assertThatThrownBy(() -> userService.getUserByUserId(userId))
                .isInstanceOf(CommonException.class)
                .hasMessage(UserErrorCode.INVALID_USER.getMessage());
        verify(userRepository, times(1)).findByUserId(userId);
    }

    @Test
    @DisplayName("사용자를 조회한다.")
    void getUserByUserId() {
        // Given
        Long userId = 1L;
        User mockUser = new User(userId, "임후에에에엥");
        when(userRepository.findByUserId(userId)).thenReturn(mockUser);

        // When
        UserResult result = userService.getUserByUserId(userId);

        // Then
        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals("임후에에에엥", result.getName());
        verify(userRepository, times(1)).findByUserId(userId);
    }

}