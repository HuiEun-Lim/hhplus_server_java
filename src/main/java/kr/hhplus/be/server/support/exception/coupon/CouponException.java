package kr.hhplus.be.server.support.exception.coupon;

import kr.hhplus.be.server.support.ErrorCode;
import lombok.Getter;

@Getter
public class CouponException extends RuntimeException {
    private final ErrorCode errorCode;
    private final Object[] args;

    public CouponException(ErrorCode errorCode, Object... args) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.args = args;
    }
}
