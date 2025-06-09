package io.github.angel.raa.service.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import io.github.angel.raa.controller.CategoryController;
import io.github.angel.raa.dto.response.CategoryResponse;

@Component
public class CategoryModelAssembler
        implements RepresentationModelAssembler<CategoryResponse, EntityModel<CategoryResponse>> {

    @Override
    public EntityModel<CategoryResponse> toModel(@org.springframework.lang.NonNull CategoryResponse category) {
        return EntityModel.of(category,
            linkTo(methodOn(CategoryController.class).getCategoryBySlug(category.getSlug())).withSelfRel(),
            linkTo(methodOn(CategoryController.class).getAllCategories(0, 10)).withRel("category")
        );
    }

}
