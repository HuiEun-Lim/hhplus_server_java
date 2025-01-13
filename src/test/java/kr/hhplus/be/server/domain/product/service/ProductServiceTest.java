package kr.hhplus.be.server.domain.product.service;

import kr.hhplus.be.server.domain.product.dto.ProductResult;
import kr.hhplus.be.server.domain.product.entity.Product;
import kr.hhplus.be.server.domain.product.entity.Stock;
import kr.hhplus.be.server.domain.product.repository.ProductRepository;
import kr.hhplus.be.server.domain.product.repository.StockRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private ProductService productService;


    @Test
    @DisplayName("상품 ID로 상품 정보를 조회하고 재고 정보를 포함한다.")
    void getProductByProductIdWithLock() {
        // Given
        Long productId = 1L;

        Product mockProduct = new Product(productId, "라이언 코치님의 피드백", 100000L);
        Stock mockStock = new Stock(1L, productId, 50L);

        // When
        when(productRepository.findByProductIdWithLock(productId)).thenReturn(mockProduct);
        when(stockRepository.findByProductId(productId)).thenReturn(mockStock);

        ProductResult result = productService.getProductByProductId(productId);

        // Then
        assertNotNull(result);
        assertEquals(productId, result.getProductId());
        assertEquals("라이언 코치님의 피드백", result.getProductName());
        assertEquals(50, result.getStock());

        verify(productRepository, times(1)).findByProductIdWithLock(productId);
        verify(stockRepository, times(1)).findByProductId(productId);
    }

    @Test
    @DisplayName("재고 정보가 포함된 상품 목록을 페이징 처리하여 조회한다.")
    void getProductList() {
        // Given
        PageRequest pageable = PageRequest.of(0, 5);

        Product product1 = new Product(1L, "고기반찬", 15000L);
        Product product2 = new Product(2L, "야채곱창", 20000L);

        Stock stock1 = new Stock(1L, 1L, 50L);
        Stock stock2 = new Stock(2L, 2L, 30L);

        List<Product> productList = Arrays.asList(product1, product2);
        Page<Product> productPage = new PageImpl<>(productList, pageable, 2);

        // When
        when(productRepository.findAll(pageable)).thenReturn(productPage);
        when(stockRepository.findByProductId(1L)).thenReturn(stock1);
        when(stockRepository.findByProductId(2L)).thenReturn(stock2);

        Page<ProductResult> result = productService.getProductList(pageable);

        // Then
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals("고기반찬", result.getContent().get(0).getProductName());
        assertEquals(50, result.getContent().get(0).getStock());
        assertEquals("야채곱창", result.getContent().get(1).getProductName());
        assertEquals(30, result.getContent().get(1).getStock());

        verify(productRepository, times(1)).findAll(pageable);
        verify(stockRepository, times(1)).findByProductId(1L);
        verify(stockRepository, times(1)).findByProductId(2L);
    }

    @Test
    @DisplayName("재고를 감소한다.")
    void decreaseStock() {
        // Given
        Long productId = 1L;
        Long quantity = 10L;

        Stock mockStock = new Stock(1L, productId, 50L);
        Product mockProduct = new Product(productId, "맥북 프로", 3000000L);

        // When
        when(stockRepository.findByProductIdWithLock(productId)).thenReturn(mockStock);
        when(productRepository.findByProductId(productId)).thenReturn(mockProduct);
        when(stockRepository.save(any(Stock.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProductResult result = productService.decreaseStock(productId, quantity);

        // Then
        assertNotNull(result);
        assertEquals(productId, result.getProductId());
        assertEquals("맥북 프로", result.getProductName());
        assertEquals(40, result.getStock());

        verify(stockRepository, times(1)).findByProductIdWithLock(productId);
        verify(stockRepository, times(1)).save(any(Stock.class));
        verify(productRepository, times(1)).findByProductId(productId);
    }

}