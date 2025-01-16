package kr.hhplus.be.server.application.order.facade;

import kr.hhplus.be.server.application.IntegrationTestSupport;
import kr.hhplus.be.server.application.order.dto.request.OrderFacadeRequest;
import kr.hhplus.be.server.application.order.dto.request.OrderProductDto;
import kr.hhplus.be.server.application.order.dto.response.OrderFacadeResponse;
import kr.hhplus.be.server.domain.coupon.entity.Coupon;
import kr.hhplus.be.server.domain.coupon.entity.CouponIssuance;
import kr.hhplus.be.server.domain.coupon.enums.CouponStateType;
import kr.hhplus.be.server.domain.coupon.enums.DiscountType;
import kr.hhplus.be.server.domain.coupon.repository.CouponIssuanceRepository;
import kr.hhplus.be.server.domain.coupon.repository.CouponRepository;
import kr.hhplus.be.server.domain.order.dto.response.OrderItem;
import kr.hhplus.be.server.domain.product.entity.Product;
import kr.hhplus.be.server.domain.product.entity.Stock;
import kr.hhplus.be.server.domain.product.repository.ProductRepository;
import kr.hhplus.be.server.domain.product.repository.StockRepository;
import kr.hhplus.be.server.domain.user.entity.User;
import kr.hhplus.be.server.infrastructure.db.coupon.jpa.CouponIssuanceJpaRepository;
import kr.hhplus.be.server.infrastructure.db.coupon.jpa.CouponJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderFacadeIntegrationTest extends IntegrationTestSupport {
    @Autowired
    private OrderFacade orderFacade;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponIssuanceRepository couponIssuanceRepository;


    @Autowired
    private CouponJpaRepository couponJpaRepository;

    @Autowired
    private CouponIssuanceJpaRepository couponIssuanceJpaRepository;

    @BeforeEach
    void setUp() {
        couponIssuanceJpaRepository.deleteAll();
        couponJpaRepository.deleteAll();
    }

    @Test
    @DisplayName("쿠폰을 사용하여 주문을 성공적으로 생성한다.")
    void createOrderWithCoupon() {
        // Given
        User user = userRepository.save(User.create("임히니"));

        Product product1 = productRepository.save(Product.create("상품1", 5000L));
        Product product2 = productRepository.save(Product.create("상품2", 3000L));

        Stock stock1 = stockRepository.save(Stock.builder().productId(product1.getProductId()).quantity(50L).build());
        Stock stock2 = stockRepository.save(Stock.builder().productId(product2.getProductId()).quantity(70L).build());

        Coupon coupon = couponRepository.save(Coupon.create("1000원 할인 쿠폰", DiscountType.AMOUNT, 1000L, 1000L, 50L, LocalDateTime.now().plusDays(10)));
        CouponIssuance issuedCoupon = couponIssuanceRepository.save(CouponIssuance.builder().userId(user.getUserId()).couponId(coupon.getCouponId()).couponState(CouponStateType.UNUSED).build());

        List<OrderProductDto> productDtos = Arrays.asList(
                new OrderProductDto(product1.getProductId(), 2L),
                new OrderProductDto(product2.getProductId(), 1L)
        );

        OrderFacadeRequest request = OrderFacadeRequest.create(user.getUserId(), issuedCoupon.getIssuanceId(), productDtos);

        // When
        OrderFacadeResponse response = orderFacade.createOrder(request);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getUserId()).isEqualTo(user.getUserId());
        assertThat(response.getOriginPrice()).isEqualTo(13000L);
        assertThat(response.getDiscountPrice()).isEqualTo(1000L);
        assertThat(response.getSalePrice()).isEqualTo(12000L);
        assertThat(response.getItems()).hasSize(2);

        OrderItem item1 = response.getItems().get(0);
        assertThat(item1.getProductId()).isEqualTo(product1.getProductId());
        assertThat(item1.getQuantity()).isEqualTo(2L);

        OrderItem item2 = response.getItems().get(1);
        assertThat(item2.getProductId()).isEqualTo(product2.getProductId());
        assertThat(item2.getQuantity()).isEqualTo(1L);
    }

    @Test
    @DisplayName("쿠폰을 사용하지 않고 주문을 성공적으로 생성한다.")
    void createOrderWithoutCoupon() {
        // Given
        User user = userRepository.save(User.create("테스트 사용자"));

        Product product1 = productRepository.save(Product.create("상품1", 5000L));
        Product product2 = productRepository.save(Product.create("상품2", 3000L));

        Stock stock1 = stockRepository.save(Stock.builder().productId(product1.getProductId()).quantity(50L).build());
        Stock stock2 = stockRepository.save(Stock.builder().productId(product2.getProductId()).quantity(70L).build());

        List<OrderProductDto> productDtos = Arrays.asList(
                new OrderProductDto(product1.getProductId(), 2L),
                new OrderProductDto(product2.getProductId(), 1L)
        );

        OrderFacadeRequest request = OrderFacadeRequest.create(user.getUserId(), null, productDtos);

        // When
        OrderFacadeResponse response = orderFacade.createOrder(request);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getUserId()).isEqualTo(user.getUserId());
        assertThat(response.getOriginPrice()).isEqualTo(13000L);
        assertThat(response.getDiscountPrice()).isEqualTo(0L);
        assertThat(response.getSalePrice()).isEqualTo(13000L);
        assertThat(response.getItems()).hasSize(2);

        OrderItem item1 = response.getItems().get(0);
        assertThat(item1.getProductId()).isEqualTo(product1.getProductId());
        assertThat(item1.getQuantity()).isEqualTo(2L);

        OrderItem item2 = response.getItems().get(1);
        assertThat(item2.getProductId()).isEqualTo(product2.getProductId());
        assertThat(item2.getQuantity()).isEqualTo(1L);
    }

}