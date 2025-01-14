package kr.hhplus.be.server.infrastructure.db.product.impl;

import kr.hhplus.be.server.domain.product.entity.Product;
import kr.hhplus.be.server.domain.product.repository.ProductRepository;
import kr.hhplus.be.server.infrastructure.db.product.jpa.ProductJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    public ProductRepositoryImpl(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    @Override
    public Product findByProductId(Long productId) {
        return productJpaRepository.findByProductId(productId);
    }

    @Override
    public Product findByProductIdWithLock(Long productId) {
        return productJpaRepository.findByProductIdWithLock(productId);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productJpaRepository.findAll(pageable);
    }

    @Override
    public Product save(Product product) {
        return productJpaRepository.save(product);
    }
}
