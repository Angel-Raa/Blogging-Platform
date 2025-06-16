package io.github.angel.raa.service.impl;

import io.github.angel.raa.dto.request.category.CategoryDTO;
import io.github.angel.raa.dto.response.CategoryResponse;
import io.github.angel.raa.dto.response.Response;
import io.github.angel.raa.exception.DuplicateSlugException;
import io.github.angel.raa.exception.ResourceNotFoundException;
import io.github.angel.raa.persistence.entity.Category;
import io.github.angel.raa.persistence.repository.CategoryRepository;
import io.github.angel.raa.service.CategoryService;
import io.github.angel.raa.utils.Slugify;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.time.LocalDateTime.now;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    private final PagedResourcesAssembler<CategoryResponse> pagedResourcesAssembler;

    public CategoryServiceImpl(CategoryRepository repository,
            PagedResourcesAssembler<CategoryResponse> pagedResourcesAssembler) {
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
                .code(201)
                .data(response)
                .timestamp(now())
                .buildResponse();
    }

    @Transactional
    @Override
    public Response<CategoryResponse> updateCategory(CategoryDTO dto, String slug) {
        Category category = repository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with slug: " + slug));

        if (repository.existsBySlug(slug)) {
            throw new DuplicateSlugException("Slug already exists");

        }
        category.setName(dto.name());
        category.setSlug(Slugify.slugify(dto.name()));
        Category updateCategory = repository.save(category);
        CategoryResponse response = CategoryResponse.fromCategory(updateCategory);
        return Response.<CategoryResponse>builder()
                .message("Category updated successfully")
                .data(response)
                .code(200)
                .timestamp(now())
                .buildResponse();

    }

    @Transactional(readOnly = true)
    @Override
    public CategoryResponse getCategoryBySlug(String slug) {
        Category category = repository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with slug: " + slug));

        System.out.println("Category: " + category);
        CategoryResponse body = CategoryResponse.fromCategory(category);

        return body;

    }
    
    @Override
    public Response<String> deleteCategory(String slug) {
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public PagedModel<EntityModel<CategoryResponse>> getAllCategories(Pageable pageable) {
        Page<Category> categories = repository.findAll(pageable);
        Page<CategoryResponse> responsePage = categories
                .map(category -> new CategoryResponse(category.getName(), category.getSlug(),
                        category.getCategoryId()));
        return pagedResourcesAssembler.toModel(responsePage);
    }

}
