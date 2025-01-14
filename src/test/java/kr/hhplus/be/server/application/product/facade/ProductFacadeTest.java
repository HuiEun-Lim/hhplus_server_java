package kr.hhplus.be.server.application.product.facade;

import kr.hhplus.be.server.application.product.dto.ProductFacadeResponse;
import kr.hhplus.be.server.domain.product.dto.ProductResult;
import kr.hhplus.be.server.domain.product.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductFacadeTest {

    @InjectMocks
    private ProductFacade productFacade;

    @Mock
    private ProductService productService;

    @Test
    @DisplayName("페이징된 상품 목록을 조회한다.")
    void testGetProducts() {
        // Given
        Pageable pageable = PageRequest.of(0, 5);

        List<ProductResult> mockProductResults = Arrays.asList(
                new ProductResult(1L, "장미 꽃 한 송이", 100L, 50L),
                new ProductResult(2L, "구름 한 주먹", 200L, 30L)
        );
        Page<ProductResult> mockPage = new PageImpl<>(mockProductResults, pageable, 2);

        when(productService.getProductList(pageable)).thenReturn(mockPage);

        // When
        Page<ProductFacadeResponse> result = productFacade.getProducts(pageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent().get(0).getProductId()).isEqualTo(1L);
        assertThat(result.getContent().get(0).getProductName()).isEqualTo("장미 꽃 한 송이");

        verify(productService, times(1)).getProductList(pageable);
    }

}