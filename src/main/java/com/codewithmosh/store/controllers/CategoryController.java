package com.codewithmosh.store.controllers;

import com.codewithmosh.store.entities.Category;
import com.codewithmosh.store.responses.ApiResponse;
import com.codewithmosh.store.services.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    private final CategoryService categoryService;
    
    @GetMapping
    public ResponseEntity<ApiResponse> getAllCategories(){
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(new ApiResponse("Founds!", categories));
    }
    
    @GetMapping("{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id){
        Category category = categoryService.getCategoryById(id);
        if (category == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ApiResponse("Found!", category));
    }
    
    @GetMapping("{name}")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){
        Category category = categoryService.getCategoryByName(name);
        if (category == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ApiResponse("Found!", category));
    }
    
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> createCategory(
            @RequestBody Category request,
            UriComponentsBuilder uriBuilder){
        Category category = categoryService.createCategory(request);
        var uri = uriBuilder.path("${api.prefix}/categories/{id}").buildAndExpand(category.getId()).toUri();
        return ResponseEntity.created(uri).body(new ApiResponse("Created successfully!", category));
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@RequestBody Category request, @PathVariable Long id){
        Category category = categoryService.updateCategory(request, id);
        if (category == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ApiResponse("Updated successfully!", category));
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id){
        boolean deleted = categoryService.deleteCategoryById(id);
        if (!deleted){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
