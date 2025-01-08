package kr.hhplus.be.server.domain.point.dto;

import kr.hhplus.be.server.domain.point.entity.UserPoint;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserPointResult {
    private Long pointId;
    private Long userId;
    private Long amount;

    public static UserPointResult toResult(UserPoint entity) {
        return UserPointResult.builder()
                .pointId(entity.getPointId())
                .userId(entity.getUserId())
                .amount(entity.getAmount())
                .build();
    }

    public static UserPointResult create(Long userId, Long amount) {
        return UserPointResult.builder()
                .userId(userId)
                .amount(amount)
                .build();
    }
}
