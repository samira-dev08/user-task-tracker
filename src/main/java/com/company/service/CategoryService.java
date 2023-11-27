package com.company.service;

import com.company.dto.request.CategoryRequest;
import com.company.entity.Category;

import java.util.List;


public interface CategoryService {
    Category create(CategoryRequest categoryRequest);
    void  addCategoryToTask(Integer taskId, List<CategoryRequest> categoryRequest);
    void removeCategoryFromTask( Integer taskId, Integer categoryId);
}
