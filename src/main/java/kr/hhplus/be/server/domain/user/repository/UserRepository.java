package kr.hhplus.be.server.domain.user.repository;

import kr.hhplus.be.server.domain.user.entity.User;

public interface UserRepository {
    User findByUserId(Long userId);

    User save(User user);
}
