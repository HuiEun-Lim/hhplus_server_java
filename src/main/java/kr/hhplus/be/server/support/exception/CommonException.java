package kr.hhplus.be.server.support.exception;

import kr.hhplus.be.server.support.ErrorCode;
import lombok.Getter;

@Getter
public class CommonException extends IllegalStateException {
    private final ErrorCode errorCode;
    private final Object[] args;

    public CommonException(ErrorCode errorCode, Object... args) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.args = args;
    }
}
