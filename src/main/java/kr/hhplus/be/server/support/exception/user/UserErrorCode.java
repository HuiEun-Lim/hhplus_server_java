package kr.hhplus.be.server.support.exception.user;

import kr.hhplus.be.server.support.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    USER_ID_LESS_THAN_ZERO(HttpStatus.BAD_REQUEST, "사용자 ID는 0보다 커야합니다.")
    , INVALID_USER(HttpStatus.BAD_REQUEST, "존재하지 않는 사용자입니다.")
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
