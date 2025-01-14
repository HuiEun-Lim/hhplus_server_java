package kr.hhplus.be.server.support.exception.coupon;

import kr.hhplus.be.server.support.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CouponErrorCode implements ErrorCode {
    COUPON_IS_NULL(HttpStatus.BAD_REQUEST, "존재하지 않은 쿠폰입니다.")
    , EXPIRY_COUPON(HttpStatus.BAD_REQUEST, "유효하지 않은 쿠폰입니다.")
    , MAX_ISSUED_COUPON(HttpStatus.BAD_REQUEST, "발급 수량이 초과된 쿠폰입니다.")
    , ALREADY_USED_COUPON(HttpStatus.BAD_REQUEST, "이미 사용된 쿠폰입니다.")
    , ISSUED_COUPON_IS_NULL(HttpStatus.BAD_REQUEST, "발급되지 않은 쿠폰입니다.")
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
