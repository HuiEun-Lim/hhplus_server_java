package kr.hhplus.be.server.domain.coupon.repository;

import kr.hhplus.be.server.domain.coupon.entity.Coupon;
import kr.hhplus.be.server.domain.coupon.enums.DiscountType;
import kr.hhplus.be.server.infrastructure.db.coupon.impl.CouponRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(CouponRepositoryImpl.class)
class CouponRepositoryTest {

    @Autowired
    private CouponRepository repository;

    @Test
    @DisplayName("쿠폰 아이디를 통해 쿠폰 정보를 조회한다.")
    void findCouponByCouponId(){
        // given
        Coupon coupon = Coupon.create("테스트 쿠폰", DiscountType.AMOUNT, 1000L, 1000L, 50L, LocalDateTime.now().plusDays(3));
        Coupon savedCoupon = repository.save(coupon);

        // when
        Coupon result = repository.findByCouponId(savedCoupon.getCouponId());

        // then
        assertNotNull(result);
        assertEquals(savedCoupon.getCouponName(), result.getCouponName());
        assertEquals(savedCoupon.getDiscountAmount(), result.getDiscountAmount());

    }

    @Test
    @DisplayName("쿠폰 아이디를 통해 쿠폰 정보를 lock을 걸어 조회한다.")
    void findCouponByCouponIdWithLock(){
        // given
        Coupon coupon = Coupon.create("테스트 쿠폰", DiscountType.AMOUNT, 1000L, 1000L, 50L, LocalDateTime.now().plusDays(3));
        Coupon savedCoupon = repository.save(coupon);

        // when
        Coupon result = repository.findByCouponIdWithLock(savedCoupon.getCouponId());

        // then
        assertNotNull(result);
        assertEquals(savedCoupon.getCouponName(), result.getCouponName());
        assertEquals(savedCoupon.getDiscountAmount(), result.getDiscountAmount());

    }
}