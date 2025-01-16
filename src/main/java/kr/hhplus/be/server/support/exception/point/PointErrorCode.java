package kr.hhplus.be.server.support.exception.point;

import kr.hhplus.be.server.support.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PointErrorCode implements ErrorCode {

    INVALID_USER_POINT(HttpStatus.BAD_REQUEST, "사용자의 포인트를 확인할 수 없습니다.")
    , CHARGE_POINT_ZERO(HttpStatus.BAD_REQUEST, "충전할 포인트가 없습니다. (충전 포인트 0)")
    , CHARGE_POINT_MINUS(HttpStatus.BAD_REQUEST, "충전할 포인트가 음수일 수 없습니다.")
    , CHARGE_POINT_UNDER_TEN(HttpStatus.BAD_REQUEST, "포인트 10원 미만은 충전할 수 없습니다.")
    , CHARGE_POINT_UNIT_TEN(HttpStatus.BAD_REQUEST, "포인트는 10원 단위로 충전할 수 있습니다.")
    , USE_POINT_LACK(HttpStatus.BAD_REQUEST, "충전된 포인트보다 많은 포인트를 사용할 수 없습니다.")
    , USER_POINT_FAIL(HttpStatus.BAD_REQUEST, "포인트 충전/사용이 실패하였습니다.")
    ;

    private final HttpStatus status;
    private final String message;

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
