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
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
// TODO: LOGICA IMPL DE CATEGORY
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    private final PagedResourcesAssembler<CategoryResponse> pagedResourcesAssembler;
    public CategoryServiceImpl(CategoryRepository repository, PagedResourcesAssembler<CategoryResponse> pagedResourcesAssembler) {
        this.repository = repository;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }
    @Transactional
    @Override
    public Response<CategoryResponse> createCategory(CategoryDTO dto) {
        Category category = new Category();
        category.setSlug(Slugify.slugify(dto.name()));
        category.setName(dto.name());
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
    public PagedModel<EntityModel<CategoryResponse>> getAllCategories(Pageable pageable) {
        Page<Category> categories = repository.findAll(pageable);
        Page<CategoryResponse> responsePage = categories.map(category ->
                new CategoryResponse(category.getName(), category.getSlug()));
        return pagedResourcesAssembler.toModel(responsePage);
    }

}
