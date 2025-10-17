package com.codewithmosh.store.services;
import com.codewithmosh.store.dtos.ProductDto;
import com.codewithmosh.store.entities.Category;
import com.codewithmosh.store.entities.Product;
import com.codewithmosh.store.mappers.ProductMapper;
import com.codewithmosh.store.repositories.CategoryRepository;
import com.codewithmosh.store.repositories.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    public List<ProductDto> getAllProducts(String filter) {
        List<Product> products = filter.isEmpty() ? productRepository.findAllWithCategory(): productRepository.findByCategoryId(Long.valueOf(filter));
        return products.stream().map(productMapper::toDto).toList();
    }

    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        return productMapper.toDto(product);
    }
    
    public ProductDto createProduct(ProductDto request) {
        Category category = categoryRepository.findById(request.getCategoryId()).orElse(null);
        if (category == null){
            return null;
        }
        Product product = productMapper.toEntity(request);
        product.setCategory(category);
        productRepository.save(product);
        return productMapper.toDto(product);
    }

    public ProductDto updateProduct(Long id, ProductDto request) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null){
            return null;
        }
        Category category = categoryRepository.findById(request.getCategoryId()).orElse(null);
        if (category == null){
            return null;
        }
        productMapper.update(request, product);
        product.setCategory(category);
        productRepository.save(product);
        return productMapper.toDto(product);
    }

    public boolean deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null){
            return false;
        }
        productRepository.delete(product);
        return true;
    }
}
