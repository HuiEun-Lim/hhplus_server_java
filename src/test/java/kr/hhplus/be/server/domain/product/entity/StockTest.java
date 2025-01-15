package kr.hhplus.be.server.domain.product.entity;

import kr.hhplus.be.server.support.exception.CommonException;
import kr.hhplus.be.server.support.exception.product.ProductErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StockTest {

    @Test
    @DisplayName("재고 수량을 정상적으로 감소시킨다.")
    void testDecreaseQuantity_Success() {
        // Given
        Stock stock = new Stock(1L, 1L, 100L);

        // When
        stock.decreaseQuantity(30L);

        // Then
        assertThat(stock.getQuantity()).isEqualTo(70L);
    }

    @Test
    @DisplayName("재고 수량이 부족할 때 예외를 발생시킨다.")
    void testDecreaseQuantity_NotEnoughStock() {
        // Given
        Stock stock = new Stock(1L, 1L, 20L);

        // When & Then
        assertThatThrownBy(() -> stock.decreaseQuantity(21L))
                .isInstanceOf(CommonException.class)
                .hasMessage(ProductErrorCode.STOCK_NOT_ENOUGH.getMessage());
    }

}