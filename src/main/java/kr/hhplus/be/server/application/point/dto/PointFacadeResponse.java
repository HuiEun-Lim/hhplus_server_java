package kr.hhplus.be.server.application.point.dto;

import kr.hhplus.be.server.domain.point.dto.UserPointResult;
import kr.hhplus.be.server.domain.user.dto.UserResult;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PointFacadeResponse {
    private Long pointId;
    private Long userId;
    private String userName;
    private Long amount;

    public static PointFacadeResponse toResponse(UserPointResult userPointResult, UserResult userResult) {
        return PointFacadeResponse.builder()
                .pointId(userPointResult.getPointId())
                .userId(userPointResult.getUserId())
                .userName(userResult.getName())
                .amount(userPointResult.getAmount())
                .build();
    }
}
