package kr.hhplus.be.server.interfaces.web.point.dto.response;

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
    private UserPointInfo pointInfo;
}
