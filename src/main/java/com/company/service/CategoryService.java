package com.company.service;

import com.company.dto.request.CategoryRequest;
import com.company.dto.response.CategoryResponse;
import com.company.entity.Category;

public interface CategoryService {
    Category getCategoryById(Integer id);
    Category create(Category category);
}
