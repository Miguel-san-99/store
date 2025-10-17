package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.ProductDto;
import com.codewithmosh.store.mappers.ProductMapper;
import com.codewithmosh.store.repositories.CategoryRepository;
import com.codewithmosh.store.repositories.ProductRepository;
import com.codewithmosh.store.services.ProductService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private final ProductService productService;

    public ProductController(ProductRepository productRepository, ProductMapper productMapper, CategoryRepository categoryRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryRepository = categoryRepository;
        this.productService = productService;
    }
    
    @GetMapping
    public List<ProductDto> getAllProducts(@RequestParam(required = false, defaultValue = "") String filter){
        
        List<ProductDto> products = productService.getAllProducts(filter);
        return products;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id){
        ProductDto productDto = productService.getProductById(id);
        if (productDto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDto);
    }
    
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto request,
                                                    UriComponentsBuilder uriBuilder){
        ProductDto productDto = productService.createProduct(request);
        var uri = uriBuilder.path("/products/{id}").buildAndExpand(productDto.getId()).toUri();
        return ResponseEntity.created(uri).body(request);
    }
    
    @PutMapping("{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable(name = "id") Long id,
                                                    @RequestBody ProductDto request){
        ProductDto productDto = productService.updateProduct(id, request);
        if (productDto == null){
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(request);
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(name = "id") Long id){
        boolean deleted = productService.deleteProduct(id);
        if (!deleted){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
