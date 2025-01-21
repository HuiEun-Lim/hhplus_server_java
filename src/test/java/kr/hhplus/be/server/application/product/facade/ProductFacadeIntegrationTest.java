package kr.hhplus.be.server.application.product.facade;

import kr.hhplus.be.server.application.product.dto.ProductFacadeResponse;
import kr.hhplus.be.server.domain.order.entity.OrderProduct;
import kr.hhplus.be.server.domain.order.repository.OrderProductRepository;
import kr.hhplus.be.server.domain.product.entity.Product;
import kr.hhplus.be.server.domain.product.repository.ProductRepository;
import kr.hhplus.be.server.infrastructure.db.order.jpa.OrderProductJpaRepository;
import kr.hhplus.be.server.infrastructure.db.product.jpa.ProductJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductFacadeIntegrationTest {

    @Autowired
    private ProductFacade productFacade;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private OrderProductJpaRepository orderProductJpaRepository;

    @BeforeEach
    void setUp() {
        productJpaRepository.deleteAllInBatch();
        orderProductJpaRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("상품 목록을 성공적으로 조회한다.")
    void getProducts() {
        // Given
        productRepository.save(Product.create("삼겹살", 1000L));
        productRepository.save(Product.create("항정살", 2000L));
        productRepository.save(Product.create("목살", 3000L));

        PageRequest pageable = PageRequest.of(0, 10);

        // When
        Page<ProductFacadeResponse> response = productFacade.getProducts(pageable);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getContent()).hasSize(3);
        assertThat(response.getContent().get(0).getProductName()).isEqualTo("삼겹살");
    }

    @Test
    @DisplayName("최근 3일간 판매량 상위 5개 상품을 조회한다.")
    void getTopProducts() {
        // Given
        Product product1 = productRepository.save(Product.create("족발", 1000L));
        Product product2 = productRepository.save(Product.create("닭발", 2000L));
        Product product3 = productRepository.save(Product.create("막창", 3000L));

        orderProductRepository.save(OrderProduct.create(1L, product1.getProductId(), 10L));
        orderProductRepository.save(OrderProduct.create(2L, product2.getProductId(), 20L));
        orderProductRepository.save(OrderProduct.create(2L, product3.getProductId(), 5L));

        // When
        List<ProductFacadeResponse> response = productFacade.getTopProducts();

        // Then
        assertThat(response).isNotNull();
        assertThat(response).hasSize(3);
        assertThat(response.get(0).getProductName()).isEqualTo("닭발");
        assertThat(response.get(1).getProductName()).isEqualTo("족발");
        assertThat(response.get(2).getProductName()).isEqualTo("막창");
    }
}