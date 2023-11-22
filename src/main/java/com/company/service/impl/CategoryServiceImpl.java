package com.company.service.impl;

import com.company.dto.response.CategoryResponse;
import com.company.entity.Category;
import com.company.repository.CategoryRepository;
import com.company.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Integer id) {
        return categoryRepository.findById(id).get();
    }

    @Override
    public Category create(Category category) {
        return categoryRepository.save(category);
    }
}
