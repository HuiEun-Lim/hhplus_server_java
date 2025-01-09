package kr.hhplus.be.server.infrastructure.db.coupon.jpa;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.domain.coupon.entity.CouponIssuance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CouponIssuanceJpaRepository  extends JpaRepository<CouponIssuance, Long>  {

    List<CouponIssuance> findByUserId(Long userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT ci FROM CouponIssuance ci WHERE ci.issuanceId = :issuanceId")
    CouponIssuance findByIssuanceIdWithLock(Long issuanceId);

    Long countByCouponId(Long couponId);

    CouponIssuance findByUserIdAndCouponId(Long userId, Long couponId);
}
