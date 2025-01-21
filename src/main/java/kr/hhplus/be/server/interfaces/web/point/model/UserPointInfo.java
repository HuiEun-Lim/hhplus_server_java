package kr.hhplus.be.server.interfaces.web.point.model;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hhplus.be.server.application.point.dto.PointFacadeResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserPointInfo {
    @Schema(description = "포인트 ID")
    private Long pointId;

    @Schema(description = "사용자 ID")
    private Long userId;

    @Schema(description = "사용자 이름")
    private String userName;

    @Schema(description = "포인트 잔액")
    private Long amount;

    public static UserPointInfo toData(PointFacadeResponse facadeResponse){
        return UserPointInfo.builder()
                .pointId(facadeResponse.getPointId())
                .userId(facadeResponse.getUserId())
                .userName(facadeResponse.getUserName())
                .amount(facadeResponse.getAmount())
                .build();
    }
}
