package kr.hhplus.be.server.interfaces.web.point.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@NoArgsConstructor
public class PointRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long chargeAmount;
}
