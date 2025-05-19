package io.github.angel.raa.dto.request.category;

import io.github.angel.raa.persistence.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CategoryDTO(
        @NotNull(message = "Name is required")
        @Pattern(regexp = "^[a-zA-Z]+(?:\\s[a-zA-Z]+)*$", message = "Name is not valid")
        @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
        @NotBlank(message = "Name is required")
        String name)
{


    public static CategoryDTO fromCategory(Category category) {
        if (category == null) {
            return null;
        }
        return new CategoryDTO(category.getName());
    }
}
