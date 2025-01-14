package kr.hhplus.be.server.support.exception.point;

import kr.hhplus.be.server.support.ErrorCode;
import lombok.Getter;

@Getter
public class PointException extends RuntimeException {

    private final ErrorCode errorCode;
    private final Object[] args;

    public PointException(ErrorCode errorCode, Object... args) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.args = args;
    }
}
