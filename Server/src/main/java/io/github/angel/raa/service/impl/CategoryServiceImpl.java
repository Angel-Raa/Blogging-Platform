package io.github.angel.raa.service.impl;

import io.github.angel.raa.dto.request.category.CategoryDTO;
import io.github.angel.raa.dto.response.CategoryResponse;
import io.github.angel.raa.dto.response.Response;
import io.github.angel.raa.persistence.entity.Category;
import io.github.angel.raa.persistence.repository.CategoryRepository;
import io.github.angel.raa.service.CategoryService;
import io.github.angel.raa.utils.Slugify;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
// TODO: LOGICA IMPL DE CATEGORY
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }
    @Transactional
    @Override
    public Response<CategoryResponse> createCategory(CategoryDTO dto) {
        if(repository.existsBySlug(Slugify.slugify(dto.name()))){
            throw new IllegalArgumentException("Category already exists");
        }

        Category category = new Category();
        mapDtoToCategory(dto, category);
        repository.save(category);
        CategoryResponse response = new CategoryResponse();
        response.setName(category.getName());
        response.setSlug(category.getSlug());
        return Response.<CategoryResponse>builder().message("Category created successfully")
                .success(true)
                .code(200)
                .data(response)
                .timestamp(LocalDateTime.now())
                .buildResponse();
    }

    @Override
    public Response<CategoryResponse> updateCategory(CategoryDTO dto, String slug) {
        return null;
    }

    @Override
    public Response<CategoryResponse> getCategoryBySlug(String slug) {
        return null;
    }

    @Override
    public Response<String> deleteCategory(String slug) {
        return null;
    }
    @Transactional(readOnly = true)
    @Override
    public Page<CategoryResponse> getAllCategories(Pageable pageable) {
        Page<Category> categories = repository.findAll(pageable);
        return  categories.map(category -> new CategoryResponse(category.getName(), category.getSlug()));
    }

    private void mapDtoToCategory(@NotNull CategoryDTO dto, @NotNull Category category) {
        category.setName(dto.name());
        category.setSlug(Slugify.slugify(dto.name()));

    }
}
