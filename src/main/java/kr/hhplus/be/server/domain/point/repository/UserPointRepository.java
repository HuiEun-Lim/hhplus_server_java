package kr.hhplus.be.server.domain.point.repository;

import kr.hhplus.be.server.domain.point.entity.UserPoint;

public interface UserPointRepository {

    UserPoint findByUserId(Long userId);

    UserPoint save(UserPoint userPoint);

    UserPoint findByUserIdWithLock(Long userId);
}
