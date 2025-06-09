package io.github.angel.raa.controller;

import io.github.angel.raa.dto.request.category.CategoryDTO;
import io.github.angel.raa.dto.response.CategoryResponse;
import io.github.angel.raa.dto.response.Response;
import io.github.angel.raa.service.CategoryService;
import io.github.angel.raa.service.assemblers.CategoryModelAssembler;
import jakarta.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
// TODO: CORREGIR PROBLEMA DE BUSCA SLUG
@RestController
@Validated
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryModelAssembler assembler;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
        this.assembler = new CategoryModelAssembler();
    }

    @PreAuthorize("permitAll")
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<CategoryResponse>>> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PagedModel<EntityModel<CategoryResponse>> categories = categoryService
                .getAllCategories(Pageable.ofSize(size).withPage(page));
        return ResponseEntity.ok(categories);
    }

    @PreAuthorize("permitAll")
    @GetMapping("/{slug}")
    public ResponseEntity<EntityModel<CategoryResponse>> getCategoryBySlug(@PathVariable String slug) {
        CategoryResponse entityModelResponse = categoryService.getCategoryBySlug(slug);
        System.out.println("slug:  "+ slug);
        var body = assembler.toModel(entityModelResponse);
        return ResponseEntity.ok(body);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER')")
    @PostMapping
    public ResponseEntity<Response<CategoryResponse>> createCategory(@Valid @RequestBody CategoryDTO category) {
        Response<CategoryResponse> response = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

}
