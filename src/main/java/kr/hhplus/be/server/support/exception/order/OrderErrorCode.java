package kr.hhplus.be.server.support.exception.order;

import kr.hhplus.be.server.support.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode implements ErrorCode {
    INVALID_ORDER_ID(HttpStatus.BAD_REQUEST, "존재하지 않는 주문 번호입니다.")
    , FAIL_UPDATE_STATUS(HttpStatus.BAD_REQUEST, "주문 상태 변경에 실패하였습니다.")
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
