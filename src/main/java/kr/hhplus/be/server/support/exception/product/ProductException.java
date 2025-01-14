package kr.hhplus.be.server.support.exception.product;

import kr.hhplus.be.server.support.ErrorCode;

public class ProductException extends RuntimeException {
    private final ErrorCode errorCode;
    private final Object[] args;

    public ProductException(ErrorCode errorCode, Object... args) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.args = args;
    }
}
