package io.github.angel.raa.controller;

import io.github.angel.raa.dto.request.category.CategoryDTO;
import io.github.angel.raa.dto.response.CategoryResponse;
import io.github.angel.raa.dto.response.Response;
import io.github.angel.raa.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@Validated
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @PreAuthorize("permitAll")
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<CategoryResponse>>> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PagedModel<EntityModel<CategoryResponse>> categories = categoryService.getAllCategories(Pageable.ofSize(size).withPage(page));
        return ResponseEntity.ok(categories);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER')")
    @PostMapping
    public ResponseEntity<Response<CategoryResponse>> createCategory(@Valid @RequestBody CategoryDTO category) {
        System.out.println("Category:   " +category);
        Response<CategoryResponse> response = categoryService.createCategory(category);
        return ResponseEntity.ok(response);

    }
}
