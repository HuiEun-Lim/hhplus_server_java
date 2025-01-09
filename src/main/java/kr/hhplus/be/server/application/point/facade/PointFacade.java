package kr.hhplus.be.server.application.point.facade;

import kr.hhplus.be.server.application.point.dto.PointFacadeResponse;
import kr.hhplus.be.server.domain.point.dto.UserPointResult;
import kr.hhplus.be.server.domain.point.service.PointService;
import kr.hhplus.be.server.domain.user.dto.UserResult;
import kr.hhplus.be.server.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("pointFacade")
@RequiredArgsConstructor
public class PointFacade {

    private final PointService pointService;
    private final UserService userService;

    @Transactional
    public PointFacadeResponse chargeUserPoint(Long userId, Long amount){
        // 사용자 정보 검증
        UserResult user = userService.getUserByUserId(userId);

        // 포인트 충전 처리
        UserPointResult pointResult = pointService.chargeUserPoint(userId, amount);

        return PointFacadeResponse.toResponse(pointResult, user);

    }

    @Transactional(readOnly = true)
    public PointFacadeResponse getUserPoint(Long userId){
        // 사용자 정보 검증
        UserResult user = userService.getUserByUserId(userId);

        // 포인트 조회
        UserPointResult pointResult = pointService.getUserPoint(userId);

        return PointFacadeResponse.toResponse(pointResult, user);

    }
}
