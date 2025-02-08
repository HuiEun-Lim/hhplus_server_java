package kr.hhplus.be.server.infrastructure.db.coupon.impl;

import kr.hhplus.be.server.domain.coupon.entity.Coupon;
import kr.hhplus.be.server.domain.coupon.repository.CouponRepository;
import kr.hhplus.be.server.infrastructure.db.coupon.jpa.CouponJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CouponRepositoryImpl implements CouponRepository {

    private final CouponJpaRepository couponJpaRepository;

    public CouponRepositoryImpl(CouponJpaRepository couponJpaRepository) {
        this.couponJpaRepository = couponJpaRepository;
    }

    @Override
    public Coupon findByCouponId(Long couponId) {
        return couponJpaRepository.findByCouponId(couponId);
    }

    @Override
    public Coupon findByCouponIdWithLock(Long couponId) {
        return couponJpaRepository.findByCouponIdWithLock(couponId);
    }

    @Override
    public Coupon save(Coupon coupon) {
        return couponJpaRepository.save(coupon);
    }

    @Override
    public Long findMaxIssuanceCountByCouponId(Long couponId) {
        return couponJpaRepository.findMaxIssuanceCountByCouponId(couponId);
    }
}
