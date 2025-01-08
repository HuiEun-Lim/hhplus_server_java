package kr.hhplus.be.server.infrastructure.db.point.impl;

import kr.hhplus.be.server.domain.point.entity.UserPoint;
import kr.hhplus.be.server.domain.point.repository.UserPointRepository;
import kr.hhplus.be.server.infrastructure.db.point.jpa.UserPointJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserPointRepositoryImpl implements UserPointRepository {

    private final UserPointJpaRepository userPointJpaRepository;

    public UserPointRepositoryImpl(UserPointJpaRepository userPointJpaRepository) {
        this.userPointJpaRepository = userPointJpaRepository;
    }

    @Override
    public UserPoint findByUserId(Long userId) {
        return userPointJpaRepository.findByUserId(userId);
    }

    @Override
    public UserPoint save(UserPoint userPoint) {
        return userPointJpaRepository.save(userPoint);
    }

    @Override
    public UserPoint findByUserIdWithLock(Long userId) {
        return userPointJpaRepository.findByUserIdWithLock(userId);
    }
}
