package kr.hhplus.be.server.domain.coupon.service;

import kr.hhplus.be.server.domain.coupon.dto.CouponIssuanceResult;
import kr.hhplus.be.server.domain.coupon.dto.CouponResult;
import kr.hhplus.be.server.domain.coupon.entity.Coupon;
import kr.hhplus.be.server.domain.coupon.entity.CouponIssuance;
import kr.hhplus.be.server.domain.coupon.enums.CouponStateType;
import kr.hhplus.be.server.domain.coupon.enums.DiscountType;
import kr.hhplus.be.server.domain.coupon.repository.CouponIssuanceRepository;
import kr.hhplus.be.server.domain.coupon.repository.CouponRepository;
import kr.hhplus.be.server.infrastructure.redis.RedisRepository;
import kr.hhplus.be.server.support.exception.CommonException;
import kr.hhplus.be.server.support.exception.coupon.CouponErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CouponServiceTest {

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private CouponIssuanceRepository couponIssuanceRepository;

    @InjectMocks
    private CouponService couponService;

    @Mock
    private RedisRepository redisRepository;

    @Test
    @DisplayName("쿠폰 ID로 쿠폰의 정보를 조회한다.")
    void getCouponInfoByCouponId() {
        // given
        Long couponId = 1L;
        Coupon mockCoupon = new Coupon(couponId, "깜짝 쿠폰", DiscountType.AMOUNT, 1000L, 5000L, 100L, LocalDateTime.now().plusDays(10));

        when(couponRepository.findByCouponId(couponId)).thenReturn(mockCoupon);

        // when
        CouponResult result = couponService.getCouponInfoByCouponId(couponId);

        // then
        assertNotNull(result);
        assertEquals(couponId, result.getCouponId());
        assertEquals("깜짝 쿠폰", result.getCouponName());
        verify(couponRepository, times(1)).findByCouponId(couponId);
    }

    @Test
    @DisplayName("존재하지 않는 쿠폰 ID로 쿠폰의 정보를 조회한다.")
    void getNullCouponInfoByCouponId() {
        // given
        Long couponId = 1L;

        when(couponRepository.findByCouponId(couponId)).thenReturn(null);

        // when & then
        assertThatThrownBy(() -> couponService.getCouponInfoByCouponId(couponId))
                .isInstanceOf(CommonException.class)
                .hasMessage(CouponErrorCode.COUPON_IS_NULL.getMessage());
        verify(couponRepository, times(1)).findByCouponId(couponId);
        verify(couponIssuanceRepository, never()).countByCouponId(anyLong());
        verify(couponIssuanceRepository, never()).save(any(CouponIssuance.class));
    }

    @Test
    @DisplayName("사용자가 쿠폰을 성공적으로 발행받는다.")
    void issueCoupon() {
        // given
        Long userId = 1L;
        Long couponId = 1L;
        Coupon mockCoupon = new Coupon(couponId, "설날 쿠폰", DiscountType.AMOUNT, 5000L, 5000L, 100L, LocalDateTime.now().plusDays(10));
        CouponIssuance mockIssuance = new CouponIssuance(userId, couponId, CouponStateType.UNUSED);

        when(couponRepository.findByCouponIdWithLock(couponId)).thenReturn(mockCoupon);
        when(couponIssuanceRepository.countByCouponId(couponId)).thenReturn(50L);
        when(couponIssuanceRepository.save(any(CouponIssuance.class))).thenReturn(mockIssuance);

        // when
        CouponIssuanceResult result = couponService.issueCoupon(userId, couponId);

        // then
        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals(couponId, result.getCouponId());
        verify(couponRepository, times(1)).findByCouponIdWithLock(couponId);
        verify(couponIssuanceRepository, times(1)).countByCouponId(couponId);
        verify(couponIssuanceRepository, times(1)).save(any(CouponIssuance.class));
    }

    @Test
    @DisplayName("존재하지 않는 쿠폰 ID로 쿠폰을 발행 받을 수 없다.")
    void issueNotFoundCoupon() {
        // given
        Long userId = 1L;
        Long couponId = 1L;

        // when
        when(couponRepository.findByCouponIdWithLock(couponId)).thenReturn(null);

        // then
        assertThatThrownBy(() -> couponService.issueCoupon(userId, couponId))
                .isInstanceOf(CommonException.class)
                .hasMessage(CouponErrorCode.COUPON_IS_NULL.getMessage());
        verify(couponRepository, times(1)).findByCouponIdWithLock(couponId);
        verify(couponIssuanceRepository, never()).countByCouponId(anyLong());
        verify(couponIssuanceRepository, never()).save(any(CouponIssuance.class));
    }

    @Test
    @DisplayName("사용자가 발행받은 쿠폰 목록을 조회한다.")
    void userIssuedCouponList() {
        // given
        Long userId = 1L;
        CouponIssuance issuance1 = new CouponIssuance(userId, 1L, CouponStateType.UNUSED);
        CouponIssuance issuance2 = new CouponIssuance(userId, 2L, CouponStateType.UNUSED);
        Coupon coupon1 = new Coupon(1L, "설날 쿠폰", DiscountType.AMOUNT, 1000L, 5000L, 100L, LocalDateTime.now().plusDays(10));
        Coupon coupon2 = new Coupon(2L, "새해 복 쿠폰", DiscountType.AMOUNT, 500L, 2000L, 50L, LocalDateTime.now().plusDays(5));

        when(couponIssuanceRepository.findByUserId(userId)).thenReturn(Arrays.asList(issuance1, issuance2));
        when(couponRepository.findByCouponId(1L)).thenReturn(coupon1);
        when(couponRepository.findByCouponId(2L)).thenReturn(coupon2);

        // when
        List<CouponIssuanceResult> results = couponService.userIssuedCouponList(userId);

        // then
        assertNotNull(results);
        assertEquals(2, results.size());
        verify(couponIssuanceRepository, times(1)).findByUserId(userId);
        verify(couponRepository, times(1)).findByCouponId(1L);
        verify(couponRepository, times(1)).findByCouponId(2L);
    }

    @Test
    @DisplayName("사용자가 발급받은 쿠폰을 성공적으로 사용한다.")
    void useUserIssuedCoupon() {
        // Given
        Long userId = 1L;
        Long couponId = 100L;

        Coupon mockCoupon = new Coupon(couponId, "10% 할인 쿠폰", DiscountType.RATE, 10L, 5000L, 100L, LocalDateTime.now().plusDays(10));
        CouponIssuance mockIssuance = new CouponIssuance(1L, userId, couponId, CouponStateType.UNUSED, null);

        when(couponRepository.findByCouponId(couponId)).thenReturn(mockCoupon);
        when(couponIssuanceRepository.findByUserIdAndIssuanceId(userId, mockIssuance.getIssuanceId())).thenReturn(mockIssuance);

        CouponIssuance savedIssuance = new CouponIssuance(1L, userId, couponId, CouponStateType.USE, LocalDateTime.now());
        when(couponIssuanceRepository.save(any(CouponIssuance.class))).thenReturn(savedIssuance);

        // When
        CouponIssuanceResult result = couponService.useUserIssuedCoupon(userId, savedIssuance.getIssuanceId());

        // Then
        assertNotNull(result);
        assertEquals(couponId, result.getCouponId());
        assertEquals(userId, result.getUserId());
        assertEquals(CouponStateType.USE, result.getCouponState());

        verify(couponRepository, times(1)).findByCouponId(couponId);
        verify(couponIssuanceRepository, times(1)).findByUserIdAndIssuanceId(userId, savedIssuance.getIssuanceId()); // 호출 메서드 수정
        verify(couponIssuanceRepository, times(1)).save(any(CouponIssuance.class));
    }

    @Test
    @DisplayName("발급받은 쿠폰 ID가 없을 때 예외를 발생시킨다.")
    void testUseUserIssuedCoupon_IssuanceNotFound() {
        // Given
        Long userId = 1L;
        Long issuanceId = 100L;

        when(couponIssuanceRepository.findByUserIdAndIssuanceId(userId, issuanceId)).thenReturn(null);

        // When & Then
        assertThatThrownBy(() -> couponService.useUserIssuedCoupon(userId, issuanceId))
                .isInstanceOf(CommonException.class)
                .hasMessage(CouponErrorCode.ISSUED_COUPON_IS_NULL.getMessage());

        verify(couponIssuanceRepository, times(1)).findByUserIdAndIssuanceId(userId, issuanceId);
        verify(couponRepository, never()).findByCouponId(anyLong());
        verify(couponIssuanceRepository, never()).save(any(CouponIssuance.class));
    }

    @Test
    @DisplayName("존재하지 않는 쿠폰 ID로 쿠폰을 사용하려고 할 때 예외를 발생시킨다.")
    void testUseUserIssuedCoupon_CouponNotFound() {
        // Given
        Long userId = 1L;
        Long issuanceId = 100L;

        CouponIssuance mockIssuance = CouponIssuance.builder()
                .issuanceId(issuanceId)
                .userId(userId)
                .couponId(1L)
                .couponState(CouponStateType.UNUSED)
                .build();

        when(couponIssuanceRepository.findByUserIdAndIssuanceId(userId, issuanceId)).thenReturn(mockIssuance);
        when(couponRepository.findByCouponId(mockIssuance.getCouponId())).thenReturn(null);

        // When & Then
        assertThatThrownBy(() -> couponService.useUserIssuedCoupon(userId, issuanceId))
                .isInstanceOf(CommonException.class)
                .hasMessage(CouponErrorCode.COUPON_IS_NULL.getMessage());

        verify(couponIssuanceRepository, times(1)).findByUserIdAndIssuanceId(userId, issuanceId);
        verify(couponRepository, times(1)).findByCouponId(mockIssuance.getCouponId());
        verify(couponIssuanceRepository, never()).save(any(CouponIssuance.class));
    }

    @Test
    @DisplayName("정상적으로 쿠폰 요청을 처리한다.")
    void successRequestCouponCache() {
        // Given
        Long userId = 1L;
        Long couponId = 100L;
        Coupon mockCoupon = new Coupon(couponId, "1,000원 할인 쿠폰", DiscountType.AMOUNT, 1000L, 1000L, 50L, LocalDateTime.now().plusDays(10));

        when(couponRepository.findByCouponIdWithLock(couponId)).thenReturn(mockCoupon);
        when(redisRepository.isMemberOfSet(anyString(), anyString())).thenReturn(false);
        when(redisRepository.getSetSize(anyString())).thenReturn(10L);

        // When
        boolean result = couponService.requestCouponCache(userId, couponId);

        // Then
        assertThat(result).isTrue();

        verify(redisRepository, times(1)).addToSortedSet(
                anyString(),   // key
                anyString(),   // value
                anyDouble(),   // timestamp (double 타입)
                anyLong(),     // TTL
                any(TimeUnit.class) // TimeUnit
        );
        verify(redisRepository, times(1)).addToSet(anyString(), anyString());
    }

    @Test
    @DisplayName("이미 발급된 쿠폰을 요청하면 예외가 발생한다.")
    void failAlreadyIssuedCoupon() {
        // Given
        Long userId = 1L;
        Long couponId = 100L;
        Coupon mockCoupon = new Coupon(couponId, "1,000원 할인 쿠폰", DiscountType.AMOUNT, 1000L, 1000L, 50L, LocalDateTime.now().plusDays(10));

        when(couponRepository.findByCouponIdWithLock(couponId)).thenReturn(mockCoupon);
        when(redisRepository.isMemberOfSet(anyString(), anyString())).thenReturn(true);

        // When & Then
        assertThatThrownBy(() -> couponService.requestCouponCache(userId, couponId))
                .isInstanceOf(CommonException.class)
                .hasMessage(CouponErrorCode.ALREADY_ISSUED_COUPON.getMessage());

        verify(redisRepository, never()).addToSortedSet(anyString(), anyString(), anyLong(), anyLong(), any(TimeUnit.class));
        verify(redisRepository, never()).addToSet(anyString(), anyString());
    }

    @Test
    @DisplayName("쿠폰 발급 개수가 초과되었을 때 예외가 발생한다.")
    void failCouponIssuedCountExceeded() {
        // Given
        Long userId = 1L;
        Long couponId = 100L;
        Coupon mockCoupon = new Coupon(couponId, "1,000원 할인 쿠폰", DiscountType.AMOUNT, 1000L, 1000L, 10L, LocalDateTime.now().plusDays(10));

        when(couponRepository.findByCouponIdWithLock(couponId)).thenReturn(mockCoupon);
        when(redisRepository.isMemberOfSet(anyString(), anyString())).thenReturn(false);
        when(redisRepository.getSetSize(anyString())).thenReturn(11L); // 최대 발급 수 초과

        // When & Then
        assertThatThrownBy(() -> couponService.requestCouponCache(userId, couponId))
                .isInstanceOf(CommonException.class)
                .hasMessage(CouponErrorCode.MAX_ISSUED_COUPON.getMessage());

        verify(redisRepository, never()).addToSortedSet(anyString(), anyString(), anyLong(), anyLong(), any(TimeUnit.class));
        verify(redisRepository, never()).addToSet(anyString(), anyString());
    }

    @Test
    @DisplayName("존재하지 않는 쿠폰을 요청하면 예외가 발생한다.")
    void failCouponNotFound() {
        // Given
        Long userId = 1L;
        Long couponId = 100L;

        when(couponRepository.findByCouponIdWithLock(couponId)).thenReturn(null);

        // When & Then
        assertThatThrownBy(() -> couponService.requestCouponCache(userId, couponId))
                .isInstanceOf(CommonException.class)
                .hasMessage(CouponErrorCode.COUPON_IS_NULL.getMessage());

        verify(redisRepository, never()).addToSortedSet(anyString(), anyString(), anyLong(), anyLong(), any(TimeUnit.class));
        verify(redisRepository, never()).addToSet(anyString(), anyString());
    }

    @Test
    @DisplayName("만료된 쿠폰을 요청하면 예외가 발생한다.")
    void failCouponExpired() {
        // Given
        Long userId = 1L;
        Long couponId = 100L;
        Coupon mockCoupon = new Coupon(couponId, "1,000원 할인 쿠폰", DiscountType.AMOUNT, 1000L, 1000L, 50L, LocalDateTime.now().minusDays(1)); // 만료된 쿠폰

        when(couponRepository.findByCouponIdWithLock(couponId)).thenReturn(mockCoupon);

        // When & Then
        assertThatThrownBy(() -> couponService.requestCouponCache(userId, couponId))
                .isInstanceOf(CommonException.class)
                .hasMessage(CouponErrorCode.EXPIRY_COUPON.getMessage());

        verify(redisRepository, never()).addToSortedSet(anyString(), anyString(), anyLong(), anyLong(), any(TimeUnit.class));
        verify(redisRepository, never()).addToSet(anyString(), anyString());
    }

}