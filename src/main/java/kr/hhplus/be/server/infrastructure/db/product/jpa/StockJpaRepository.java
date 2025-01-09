package kr.hhplus.be.server.infrastructure.db.product.jpa;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.domain.product.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface StockJpaRepository extends JpaRepository<Stock, Long> {
    Stock findByProductId(Long productId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Stock s WHERE s.productId = :productId")
    Stock findByProductIdWithLock(Long productId);

    Stock save(Stock stock);
}
