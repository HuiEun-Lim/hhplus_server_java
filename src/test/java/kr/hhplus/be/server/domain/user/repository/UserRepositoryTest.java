package kr.hhplus.be.server.domain.user.repository;

import kr.hhplus.be.server.domain.user.entity.User;
import kr.hhplus.be.server.infrastructure.db.user.impl.UserRepositoryImpl;
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
@Import(UserRepositoryImpl.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("사용자의 아이디로 사용자 정보를 조회한다.")
    void findByUserId() {
        // given
        User mockUser = User.create("무지");
        User savedUser = userRepository.save(mockUser);
        Long userId = savedUser.getUserId();

        // when
        User user = userRepository.findByUserId(userId);

        // then
        assertNotNull(user);
        assertEquals(savedUser.getName(), user.getName());
        assertEquals(savedUser.getUserId(), user.getUserId());

    }



}