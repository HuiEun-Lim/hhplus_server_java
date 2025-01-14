package kr.hhplus.be.server.support.exception.user;

import kr.hhplus.be.server.support.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class UserException extends RuntimeException {
    private final ErrorCode errorCode;
    private final Object[] args;

    public UserException(ErrorCode errorCode, Object... args) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.args = args;
    }
}
