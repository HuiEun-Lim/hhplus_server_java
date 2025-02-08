package kr.hhplus.be.server.domain.coupon.service;

import kr.hhplus.be.server.domain.coupon.repository.CouponIssuanceRepository;
import kr.hhplus.be.server.domain.coupon.repository.CouponRepository;
import kr.hhplus.be.server.infrastructure.redis.RedisRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CouponBatchServiceTest {
    @InjectMocks
    private CouponBatchService couponBatchService;

    @Mock
    private RedisRepository redisRepository;

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private CouponIssuanceRepository couponIssuanceRepository;

    @Test
    @DisplayName("쿠폰 요청이 없을 때 배치 실행 후 아무 일도 발생하지 않는다.")
    void processCouponRequests_NoRequests() {
        // Given
        when(redisRepository.getKeysByPattern("coupon-*-requests")).thenReturn(Set.of());

        // When
        couponBatchService.processCouponRequests();

        // Then
        verify(redisRepository, times(1)).getKeysByPattern("coupon-*-requests");
        verifyNoInteractions(couponRepository, couponIssuanceRepository);
    }

    @Test
    @DisplayName("Redis에 쿠폰 요청이 있지만 추가로 발급할 수 없는 경우 처리되지 않는다.")
    void processCoupon_NoAvailableCoupons() {
        // Given
        Long couponId = 100L;
        when(couponRepository.findMaxIssuanceCountByCouponId(couponId)).thenReturn(10L);
        when(redisRepository.getSetSize("coupon-" + couponId + "-issued")).thenReturn(5L);
        when(couponIssuanceRepository.countByCouponId(couponId)).thenReturn(10L); // DB에서는 이미 다 발급됨
        when(redisRepository.getSortedSetRange("coupon-" + couponId + "-requests", 0, 0)).thenReturn(Set.of()); // 요청 없음

        // When
        couponBatchService.processCoupon(couponId);

        // Then
        verify(redisRepository, times(1)).getSetSize("coupon-" + couponId + "-issued");
        verify(couponIssuanceRepository, times(1)).countByCouponId(couponId);
        verify(redisRepository, times(1)).getSortedSetRange("coupon-" + couponId + "-requests", 0, 0);
        verifyNoMoreInteractions(redisRepository, couponIssuanceRepository);
    }

    @Test
    @DisplayName("Redis에 쿠폰 요청이 있을 때 배치 실행 후 DB에 저장된다.")
    void processCouponRequests_Success() {
        // Given
        Long couponId = 100L;
        String userKey1 = "100:1"; // couponId:userId
        String userKey2 = "100:2";
        Set<String> couponKeys = Set.of("coupon-100-requests");
        Set<String> userRequests = Set.of(userKey1, userKey2);

        when(redisRepository.getKeysByPattern("coupon-*-requests")).thenReturn(couponKeys);
        when(couponRepository.findMaxIssuanceCountByCouponId(couponId)).thenReturn(10L);
        when(redisRepository.getSetSize("coupon-" + couponId + "-issued")).thenReturn(5L);
        when(redisRepository.getSortedSetRange("coupon-" + couponId + "-requests", 0, 10)).thenReturn(userRequests);

        // When
        couponBatchService.processCouponRequests();

        // Then
        verify(redisRepository, times(1)).getKeysByPattern("coupon-*-requests");
        verify(redisRepository, times(1)).getSortedSetRange("coupon-" + couponId + "-requests", 0, 10);

        verify(couponIssuanceRepository, times(1)).saveAll(argThat(set -> set.size() == 2));

        verify(redisRepository, times(1)).removeFromSortedSet("coupon-" + couponId + "-requests", userKey1);
        verify(redisRepository, times(1)).removeFromSortedSet("coupon-" + couponId + "-requests", userKey2);
    }

    @Test
    @DisplayName("배치 실행 후 처리된 요청이 Redis에서 삭제된다.")
    void processCouponRequests_RemoveFromRedis() {
        // Given
        Long couponId = 100L;
        String userKey = "100:1";
        Set<String> couponKeys = Set.of("coupon-100-requests");
        Set<String> userRequests = Set.of(userKey);

        when(redisRepository.getKeysByPattern("coupon-*-requests")).thenReturn(couponKeys);
        when(couponRepository.findMaxIssuanceCountByCouponId(couponId)).thenReturn(10L);
        when(redisRepository.getSetSize("coupon-" + couponId + "-issued")).thenReturn(5L);
        when(redisRepository.getSortedSetRange("coupon-" + couponId + "-requests", 0, 10)).thenReturn(userRequests);

        // When
        couponBatchService.processCouponRequests();

        // Then
        verify(redisRepository, times(1)).removeFromSortedSet("coupon-" + couponId + "-requests", userKey);
    }
}