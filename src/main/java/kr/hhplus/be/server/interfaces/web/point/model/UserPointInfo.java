package kr.hhplus.be.server.interfaces.web.point.model;

import kr.hhplus.be.server.application.point.dto.PointFacadeResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserPointInfo {
    private Long pointId;
    private Long userId;
    private String userName;
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
