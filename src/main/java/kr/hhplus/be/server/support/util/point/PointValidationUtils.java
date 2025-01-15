package kr.hhplus.be.server.support.util.point;

import kr.hhplus.be.server.support.exception.CommonException;
import kr.hhplus.be.server.support.exception.point.PointErrorCode;

public class PointValidationUtils {

    /**
     * 포인트 금액이 유효한지 검증한다.
     */
    public static void validatePointAmount(Long amount) {
        if (amount < 0) {
            throw new CommonException(PointErrorCode.CHARGE_POINT_MINUS);
        }
        if (amount == 0) {
            throw new CommonException(PointErrorCode.CHARGE_POINT_ZERO);
        }
        if (amount < 10) {
            throw new CommonException(PointErrorCode.CHARGE_POINT_UNDER_TEN);
        }
        if (amount % 10 != 0) {
            throw new CommonException(PointErrorCode.CHARGE_POINT_UNIT_TEN);
        }
    }
}
