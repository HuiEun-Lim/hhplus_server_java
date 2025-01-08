package kr.hhplus.be.server.domain.user.dto;

import kr.hhplus.be.server.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResult {
    private Long userId;
    private String name;

    public static UserResult toResult(User user){
        return UserResult.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .build();
    }

    public static UserResult create(Long userId, String name){
        return UserResult.builder()
                .userId(userId)
                .name(name)
                .build();
    }
}
