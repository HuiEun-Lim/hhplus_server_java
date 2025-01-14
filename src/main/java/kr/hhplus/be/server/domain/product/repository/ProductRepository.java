package kr.hhplus.be.server.domain.product.repository;

import kr.hhplus.be.server.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepository {
    Product findByProductId(Long productId);
    Product findByProductIdWithLock(Long productId);
    Page<Product> findAll(Pageable pageable);
    Product save(Product product);
}
