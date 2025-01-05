package kr.hhplus.be.server.interfaces.web.point.controller;

import kr.hhplus.be.server.domain.point.UserPoint;
import kr.hhplus.be.server.interfaces.web.ApiResponse;
import kr.hhplus.be.server.interfaces.web.point.dto.request.PointRequest;
import kr.hhplus.be.server.interfaces.web.point.dto.response.PointResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/point")
public class PointController {

    @PostMapping
    public ApiResponse<PointResponse> chargePoint(@RequestBody PointRequest request) {
        return ApiResponse.ok(new PointResponse("충전 성공", new UserPoint(1L, 3000L)));
    }

    @GetMapping
    public ApiResponse<PointResponse> getUserPoint(@RequestParam Long userId) {
        return ApiResponse.ok(new PointResponse("충전 성공", new UserPoint(1L, 3000L)));
    }
}
