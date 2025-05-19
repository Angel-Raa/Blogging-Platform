package io.github.angel.raa.service;

import io.github.angel.raa.dto.request.category.CategoryDTO;
import io.github.angel.raa.dto.response.CategoryResponse;
import io.github.angel.raa.dto.response.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Response<CategoryResponse> createCategory(CategoryDTO dto);
    Response<CategoryResponse> updateCategory(CategoryDTO dto, String slug);
    Response<CategoryResponse> getCategoryBySlug(String slug);
    Response<String> deleteCategory(String slug);
    Page<CategoryResponse> getAllCategories(Pageable pageable);


}
