package kr.hhplus.be.server.domain.point.repository;

import kr.hhplus.be.server.domain.point.entity.PointHistory;

import java.util.List;

public interface PointHistoryRepository {

    public List<PointHistory> findByUserId(Long userId);

    public PointHistory save(PointHistory pointHistory);
}
