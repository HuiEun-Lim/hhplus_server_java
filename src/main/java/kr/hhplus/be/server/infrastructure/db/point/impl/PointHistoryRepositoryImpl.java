package kr.hhplus.be.server.infrastructure.db.point.impl;

import kr.hhplus.be.server.domain.point.entity.PointHistory;
import kr.hhplus.be.server.domain.point.repository.PointHistoryRepository;
import kr.hhplus.be.server.infrastructure.db.point.jpa.PointHistoryJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PointHistoryRepositoryImpl implements PointHistoryRepository {

    private final PointHistoryJpaRepository pointHistoryJpaRepository;

    public PointHistoryRepositoryImpl(PointHistoryJpaRepository pointHistoryJpaRepository) {
        this.pointHistoryJpaRepository = pointHistoryJpaRepository;
    }

    @Override
    public List<PointHistory> findByUserId(Long userId) {
        return pointHistoryJpaRepository.findByUserId(userId);
    }

    @Override
    public PointHistory save(PointHistory pointHistory) {
        return pointHistoryJpaRepository.save(pointHistory);
    }
}
