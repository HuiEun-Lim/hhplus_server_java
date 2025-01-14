package kr.hhplus.be.server.support.util.user;

import kr.hhplus.be.server.support.exception.user.UserErrorCode;
import kr.hhplus.be.server.support.exception.user.UserException;

public class UserValidationUtils {

    public static void validateUserId(Long id) {
        if (id == null || id <= 0) {
            throw new UserException(UserErrorCode.USER_ID_LESS_THAN_ZERO);
        }
    }
}
