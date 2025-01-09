package kr.hhplus.be.server.domain.product.repository;

import kr.hhplus.be.server.domain.product.entity.Stock;

public interface StockRepository {
    Stock findByProductId(Long productId);
    Stock findByProductIdWithLock(Long productId);
    Stock save(Stock stock);
}
