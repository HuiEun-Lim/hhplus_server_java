package kr.hhplus.be.server.infrastructure.db.user.jpa;

import kr.hhplus.be.server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    User findByUserId(Long userId);

    User save(User user);
}
