package kr.hhplus.be.server.interfaces.web.point.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.application.point.dto.PointFacadeResponse;
import kr.hhplus.be.server.application.point.facade.PointFacade;
import kr.hhplus.be.server.interfaces.web.ApiResponse;
import kr.hhplus.be.server.interfaces.web.point.dto.request.PointRequest;
import kr.hhplus.be.server.interfaces.web.point.dto.response.PointResponse;
import kr.hhplus.be.server.interfaces.web.point.model.UserPointInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "포인트 관리", description = "포인트 관리 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/points")
public class PointController {

    private final PointFacade pointFacade;

    @Operation(summary = "포인트 충전", description = "사용자의 포인트를 충전한다.")
    @PostMapping
    public ApiResponse<PointResponse> chargePoint(@RequestBody PointRequest request) {
        PointFacadeResponse facadeResponse = pointFacade.chargeUserPoint(request.getUserId(), request.getChargeAmount());
        return ApiResponse.ok(new PointResponse(UserPointInfo.toData(facadeResponse)));
    }

    @Operation(summary = "사용자 포인트 조회", description = "사용자의 포인트를 조회한다.")
    @GetMapping
    public ApiResponse<PointResponse> getUserPoint(@RequestParam Long userId) {
        PointFacadeResponse facadeResponse = pointFacade.getUserPoint(userId);
        return ApiResponse.ok(new PointResponse(UserPointInfo.toData(facadeResponse)));
    }
}
