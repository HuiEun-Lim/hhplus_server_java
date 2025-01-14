package kr.hhplus.be.server.domain.coupon.repository;

import kr.hhplus.be.server.domain.coupon.entity.Coupon;

public interface CouponRepository {

    Coupon findByCouponId(Long couponId);

    Coupon findByCouponIdWithLock(Long couponId);

    Coupon save(Coupon coupon);
}
