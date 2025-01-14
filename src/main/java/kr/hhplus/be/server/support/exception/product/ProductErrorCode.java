package kr.hhplus.be.server.support.exception.product;

import kr.hhplus.be.server.support.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProductErrorCode implements ErrorCode {

    PRODUCT_IS_NULL(HttpStatus.BAD_REQUEST, "존재하지 않는 상품입니다.")
    , STOCK_NOT_ENOUGH(HttpStatus.BAD_REQUEST, "상품의 재고가 부족합니다.")
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
