package kr.hhplus.be.server.interfaces.web.point.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PointRequest {

    @Schema(description = "사용자 ID")
    private Long userId;

    @Schema(description = "충전 금액")
    private Long chargeAmount;

    public PointRequest (Long userId, Long amount) {
        this.userId = userId;
        this.chargeAmount = amount;
    }
}
