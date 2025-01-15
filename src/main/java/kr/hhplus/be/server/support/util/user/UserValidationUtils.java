package kr.hhplus.be.server.support.util.user;

import kr.hhplus.be.server.support.exception.CommonException;
import kr.hhplus.be.server.support.exception.user.UserErrorCode;

public class UserValidationUtils {

    public static void validateUserId(Long id) {
        if (id == null || id <= 0) {
            throw new CommonException(UserErrorCode.USER_ID_LESS_THAN_ZERO);
        }
    }
}
