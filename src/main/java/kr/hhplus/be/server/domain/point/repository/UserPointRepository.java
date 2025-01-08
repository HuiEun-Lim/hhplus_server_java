package kr.hhplus.be.server.domain.point.repository;

import kr.hhplus.be.server.domain.point.entity.UserPoint;

public interface UserPointRepository {

    public UserPoint findByUserId(Long userId);

    public UserPoint save(UserPoint userPoint);

    public UserPoint findByUserIdWithLock(Long userId);
}
