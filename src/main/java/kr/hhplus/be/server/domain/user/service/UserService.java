package kr.hhplus.be.server.domain.user.service;

import kr.hhplus.be.server.domain.user.dto.UserResult;
import kr.hhplus.be.server.domain.user.entity.User;
import kr.hhplus.be.server.domain.user.repository.UserRepository;
import kr.hhplus.be.server.support.exception.CommonException;
import kr.hhplus.be.server.support.exception.user.UserErrorCode;
import kr.hhplus.be.server.support.util.user.UserValidationUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserResult getUserByUserId(Long userId) {
        UserValidationUtils.validateUserId(userId);

        User user = userRepository.findByUserId(userId);

        if(ObjectUtils.isEmpty(user)) {
            throw new CommonException(UserErrorCode.INVALID_USER);
        }

        return UserResult.toResult(user);
    }
}
