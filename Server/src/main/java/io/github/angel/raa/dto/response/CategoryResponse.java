package io.github.angel.raa.dto.response;

import io.github.angel.raa.persistence.entity.Category;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class CategoryResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -1327323324123213213L;
    private String name;
    private String slug;
    private UUID categoryId;

    public CategoryResponse() {
    }

  

    public CategoryResponse(String name, String slug, UUID categoryId) {
        this.name = name;
        this.slug = slug;
        this.categoryId = categoryId;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getCategoryId() {
        return categoryId;
    }



    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }



    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public static CategoryResponse fromCategory(Category category) {
        return new CategoryResponse(category.getName(), category.getSlug(), category.getCategoryId());
    }
}
