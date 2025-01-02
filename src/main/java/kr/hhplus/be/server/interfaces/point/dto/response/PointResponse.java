package kr.hhplus.be.server.interfaces.point.dto.response;

import kr.hhplus.be.server.domain.point.UserPoint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointResponse {
    private String message;
    private UserPoint data;
}
