package kr.hhplus.be.server.domain.point.entity;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.BaseEntity;
import kr.hhplus.be.server.domain.point.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointHistory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    private Long userId;
    private Long amount;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    public static PointHistory create(Long userId, Long amount, TransactionType transactionType){
        return PointHistory.builder()
                .userId(userId)
                .amount(amount)
                .transactionType(transactionType)
                .build();
    }

}
