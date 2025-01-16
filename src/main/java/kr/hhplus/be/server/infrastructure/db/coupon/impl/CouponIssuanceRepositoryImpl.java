package kr.hhplus.be.server.infrastructure.db.coupon.impl;

import kr.hhplus.be.server.domain.coupon.entity.CouponIssuance;
import kr.hhplus.be.server.domain.coupon.repository.CouponIssuanceRepository;
import kr.hhplus.be.server.infrastructure.db.coupon.jpa.CouponIssuanceJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CouponIssuanceRepositoryImpl implements CouponIssuanceRepository {

    private final CouponIssuanceJpaRepository couponIssuanceJpaRepository;

    public CouponIssuanceRepositoryImpl(CouponIssuanceJpaRepository couponIssuanceJpaRepository) {
        this.couponIssuanceJpaRepository = couponIssuanceJpaRepository;
    }

    @Override
    public List<CouponIssuance> findByUserId(Long userId) {
        return couponIssuanceJpaRepository.findByUserId(userId);
    }

    @Override
    public CouponIssuance findByIssuanceIdWithLock(Long issuanceId) {
        return couponIssuanceJpaRepository.findByIssuanceIdWithLock(issuanceId);
    }

    @Override
    public CouponIssuance save(CouponIssuance entity) {
        return couponIssuanceJpaRepository.save(entity);
    }

    @Override
    public Long countByCouponId(Long couponId) {
        return couponIssuanceJpaRepository.countByCouponId(couponId);
    }

    @Override
    public CouponIssuance findByUserIdAndIssuanceId(Long userId, Long issuanceId) {
        return couponIssuanceJpaRepository.findByUserIdAndIssuanceId(userId, issuanceId);
    }
}
