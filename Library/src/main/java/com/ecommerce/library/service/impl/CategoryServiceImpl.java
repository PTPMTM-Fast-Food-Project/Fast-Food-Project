package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.CategoryDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.repository.CategoryRepository;
import com.ecommerce.library.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category save(Category category) {
        Category categorySave = new Category();
        categorySave.setName(category.getName());
        categorySave.setIs_activated(true);
        categorySave.setIs_deleted(false);
        return categoryRepository.save(categorySave);
    }

    @Override
    public Category update(Category category) {
        Category categoryUpdate = categoryRepository.getReferenceById(category.getId());
        categoryUpdate.setName(category.getName());
        return categoryRepository.save(categoryUpdate);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        Category category = categoryRepository.getReferenceById(id);
        category.setIs_activated(false);
        category.setIs_deleted(true);
        categoryRepository.save(category);
    }

    @Override
    public void enabledById(Long id) {
        Category category = categoryRepository.getReferenceById(id);
        category.setIs_activated(true);
        category.setIs_deleted(false);
        categoryRepository.save(category);
    }

    @Override
    public List<Category> findAllByActivatedTrue() {
        return categoryRepository.findAllByActivatedTrue();
    }

    @Override
    public List<CategoryDto> getCategoriesAndSize() {
        return categoryRepository.getCategoriesAndSize();
    }
}
