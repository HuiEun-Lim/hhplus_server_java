package kr.hhplus.be.server.domain.point.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import kr.hhplus.be.server.domain.BaseEntity;
import kr.hhplus.be.server.support.exception.CommonException;
import kr.hhplus.be.server.support.exception.point.PointErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPoint extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointId;

    private Long userId;
    private Long amount;

    public static UserPoint create(Long userId, Long amount){
        return UserPoint.builder()
                .userId(userId)
                .amount(amount)
                .build();
    }

    public void increase(Long amount){
        this.amount += amount;
    }

    public void decrease(Long amount){
        if(this.amount < amount){
            throw new CommonException(PointErrorCode.USE_POINT_LACK);
        }
        this.amount -= amount;
    }
}
