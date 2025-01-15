package kr.hhplus.be.server.domain.point.service;

import kr.hhplus.be.server.domain.point.dto.UserPointResult;
import kr.hhplus.be.server.domain.point.entity.PointHistory;
import kr.hhplus.be.server.domain.point.entity.UserPoint;
import kr.hhplus.be.server.domain.point.enums.TransactionType;
import kr.hhplus.be.server.domain.point.repository.PointHistoryRepository;
import kr.hhplus.be.server.domain.point.repository.UserPointRepository;
import kr.hhplus.be.server.support.util.point.PointValidationUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PointService {

    private final UserPointRepository userPointRepository;
    private final PointHistoryRepository pointHistoryRepository;

    @Transactional
    public UserPointResult chargeUserPoint(Long userId, Long amount) {

        PointValidationUtils.validatePointAmount(amount);

        UserPoint userPoint = userPointRepository.findByUserIdWithLock(userId);

        if(ObjectUtils.isEmpty(userPoint)){
            userPoint = UserPoint.create(userId, 0L);
        }

        userPoint.increase(amount);
        userPointRepository.save(userPoint);

        PointHistory pointHistory = PointHistory.create(userId, amount, TransactionType.CHARGE);
        pointHistoryRepository.save(pointHistory);

        return UserPointResult.toResult(userPoint);
    }

    @Transactional(readOnly = true)
    public UserPointResult getUserPoint(Long userId) {
        UserPoint userPoint = userPointRepository.findByUserId(userId);
        return UserPointResult.toResult(userPoint);
    }



}
