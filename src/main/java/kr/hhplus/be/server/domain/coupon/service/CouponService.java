package kr.hhplus.be.server.domain.coupon.service;

import kr.hhplus.be.server.domain.coupon.dto.CouponIssuanceResult;
import kr.hhplus.be.server.domain.coupon.dto.CouponResult;
import kr.hhplus.be.server.domain.coupon.entity.Coupon;
import kr.hhplus.be.server.domain.coupon.entity.CouponIssuance;
import kr.hhplus.be.server.domain.coupon.enums.CouponStateType;
import kr.hhplus.be.server.domain.coupon.repository.CouponIssuanceRepository;
import kr.hhplus.be.server.domain.coupon.repository.CouponRepository;
import kr.hhplus.be.server.support.exception.CommonException;
import kr.hhplus.be.server.support.exception.coupon.CouponErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CouponService {

    public final CouponRepository couponRepository;
    public final CouponIssuanceRepository couponIssuanceRepository;

    @Transactional(readOnly = true)
    public CouponResult getCouponInfoByCouponId(Long couponId) {
        Coupon coupon = couponRepository.findByCouponId(couponId);
        if (coupon == null) {
            throw new CommonException(CouponErrorCode.COUPON_IS_NULL);
        }
        return CouponResult.toResult(coupon);
    }

    @Transactional
    public CouponIssuanceResult issueCoupon(Long userId, Long couponId) {
        Coupon coupon = couponRepository.findByCouponIdWithLock(couponId);
        if (coupon == null) {
            throw new CommonException(CouponErrorCode.COUPON_IS_NULL);
        }

        coupon.checkExpiryDate();
        coupon.checkIssuedCount(couponIssuanceRepository.countByCouponId(couponId));

        CouponIssuance IssueReqInfo = new CouponIssuance(userId, couponId, CouponStateType.UNUSED);

        CouponIssuance issuedCoupon = couponIssuanceRepository.save(IssueReqInfo);
        issuedCoupon.checkCouponState();

        return CouponIssuanceResult.toResult(issuedCoupon, coupon);
    }

    @Transactional(readOnly = true)
    public List<CouponIssuanceResult> userIssuedCouponList(Long userId) {
        List<CouponIssuance> issuanceList = couponIssuanceRepository.findByUserId(userId);

        return issuanceList.stream()
                .map(issuance -> {
                    Coupon coupon = couponRepository.findByCouponId(issuance.getCouponId());
                    return CouponIssuanceResult.toResult(issuance, coupon);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public CouponIssuanceResult useUserIssuedCoupon(Long userId, Long issuanceId) {
        CouponIssuance issuance = couponIssuanceRepository.findByUserIdAndIssuanceId(userId, issuanceId);
        if (issuance == null) {
            throw new CommonException(CouponErrorCode.ISSUED_COUPON_IS_NULL);
        }

        Coupon coupon = couponRepository.findByCouponId(issuance.getCouponId());
        if (coupon == null) {
            throw new CommonException(CouponErrorCode.COUPON_IS_NULL);
        }
        coupon.checkExpiryDate();

        CouponIssuance usedCoupon = new CouponIssuance(issuance.getIssuanceId(), issuance.getUserId(), issuance.getCouponId(), CouponStateType.USE, LocalDateTime.now());

        CouponIssuance issuedCoupon = couponIssuanceRepository.save(usedCoupon);

        return CouponIssuanceResult.toResult(issuedCoupon, coupon);
    }

    @Transactional(readOnly = true)
    public CouponIssuanceResult getIssuedCouponInfoByIssuanceId(Long issuanceId) {
        CouponIssuance issuedCoupon = couponIssuanceRepository.findByIssuanceIdWithLock(issuanceId);
        if (issuedCoupon == null) {
            throw new CommonException(CouponErrorCode.ISSUED_COUPON_IS_NULL);
        }
        Coupon coupon = couponRepository.findByCouponId(issuedCoupon.getCouponId());
        if (coupon == null) {
            throw new CommonException(CouponErrorCode.COUPON_IS_NULL);
        }

        return CouponIssuanceResult.toResult(issuedCoupon, coupon);
    }
}
