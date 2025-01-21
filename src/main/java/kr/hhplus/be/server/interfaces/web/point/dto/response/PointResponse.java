package kr.hhplus.be.server.interfaces.web.point.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hhplus.be.server.interfaces.web.point.model.UserPointInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointResponse {
    @Schema(description = "사용자 포인트 정보")
    private UserPointInfo pointInfo;
}
