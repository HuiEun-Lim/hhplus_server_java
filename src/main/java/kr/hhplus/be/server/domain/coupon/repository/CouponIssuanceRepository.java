package kr.hhplus.be.server.domain.coupon.repository;

import kr.hhplus.be.server.domain.coupon.entity.CouponIssuance;

import java.util.List;

public interface CouponIssuanceRepository {

    List<CouponIssuance> findByUserId(Long userId);
    CouponIssuance findByIssuanceIdWithLock(Long issuanceId);
    CouponIssuance save(CouponIssuance entity);
    Long countByCouponId(Long couponId);
    CouponIssuance findByUserIdAndIssuanceId(Long userId, Long issuance);
}
