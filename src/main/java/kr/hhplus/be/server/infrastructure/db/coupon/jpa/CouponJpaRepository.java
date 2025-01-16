package kr.hhplus.be.server.infrastructure.db.coupon.jpa;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.domain.coupon.entity.Coupon;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface CouponJpaRepository extends JpaRepository<Coupon, Long>  {
    Coupon findByCouponId(Long couponId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Coupon c WHERE c.couponId = :couponId")
    Coupon findByCouponIdWithLock(Long couponId);

    Coupon save(@NotNull Coupon coupon);

    int countByCouponId(Long couponId);
}
