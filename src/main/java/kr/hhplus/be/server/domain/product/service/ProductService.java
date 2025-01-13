package kr.hhplus.be.server.domain.product.service;

import kr.hhplus.be.server.domain.product.dto.ProductResult;
import kr.hhplus.be.server.domain.product.entity.Product;
import kr.hhplus.be.server.domain.product.entity.Stock;
import kr.hhplus.be.server.domain.product.repository.ProductRepository;
import kr.hhplus.be.server.domain.product.repository.StockRepository;
import kr.hhplus.be.server.support.exception.product.ProductErrorCode;
import kr.hhplus.be.server.support.exception.product.ProductException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final StockRepository  stockRepository;

    @Transactional(readOnly = true)
    public ProductResult getProductByProductId(Long productId){
        Product product = productRepository.findByProductId(productId);
        if(product == null){
            throw new ProductException(ProductErrorCode.PRODUCT_IS_NULL);
        }

        Stock stock = stockRepository.findByProductId(productId);

        return ProductResult.toResult(product, stock);
    }

    @Transactional(readOnly = true)
    public Page<ProductResult> getProductList(Pageable pageable){
        Page<Product> products = productRepository.findAll(pageable);

        List<ProductResult> productResults = products.getContent().stream()
                .map(product -> {
                    Stock stock = stockRepository.findByProductId(product.getProductId());
                    return ProductResult.toResult(product, stock);
                })
                .collect(Collectors.toList());

        return new PageImpl<>(productResults, pageable, products.getTotalElements());
    }

    @Transactional
    public ProductResult decreaseStock(Long productId, Long quantity){
        Stock stock = stockRepository.findByProductIdWithLock(productId);
        stock.decreaseQuantity(quantity);

        Product product = productRepository.findByProductId(productId);

        Stock savedStock = stockRepository.save(stock);

        return ProductResult.toResult(product, savedStock);
    }

}
