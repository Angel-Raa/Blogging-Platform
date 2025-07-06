package io.github.angel.raa.dto.request.post;

import io.github.angel.raa.persistence.entity.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostUpdateDto(
        @NotBlank(message = "Slug is required") @Size(min = 3, max = 50, message = "Slug must be between 3 and 50 characters") String title,

        @NotBlank(message = "Content is required") String content,
        Post.PostStatus status) {
    // This record is used to update a post with the necessary fields.
    // The slug is included to identify the post being updated.

}
