package kr.hhplus.be.server.support.exception.payment;

import kr.hhplus.be.server.support.ErrorCode;
import lombok.Getter;

@Getter
public class PaymentException extends RuntimeException {
    private final ErrorCode errorCode;
    private final Object[] args;

    public PaymentException(ErrorCode errorCode, Object... args) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.args = args;
    }
}
