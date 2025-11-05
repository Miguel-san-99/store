package com.codewithmosh.store.services;

import com.codewithmosh.store.entities.Category;
import com.codewithmosh.store.repositories.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    
    public Category getCategoryById(Long id){
        return categoryRepository.findById(id).orElse(null);
    }
    
    public Category getCategoryByName(String name){
        return categoryRepository.findByName(name);
    }
    
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }
    
    public Category createCategory(Category request){
        Category category = categoryRepository.save(request);
        return category;
    }
    
    public Category updateCategory(Category request, Long id){
        var category = categoryRepository.findById(id).orElse(null);
        if (category == null){
            return null;
        }
        category.setName(request.getName());
        categoryRepository.save(category);
        return category;
    }
    
    public boolean deleteCategoryById(Long id){
        var category = categoryRepository.findById(id).orElse(null);
        if (category == null){
            return false;
        }
        categoryRepository.delete(category);
        return true;
    }
}
