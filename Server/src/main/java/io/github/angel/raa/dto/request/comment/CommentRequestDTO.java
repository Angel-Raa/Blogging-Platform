package io.github.angel.raa.dto.request.comment;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CommentRequestDTO {
    @NotBlank(message = "Content is required")
    @Pattern(regexp = "^[\\w\\s.,!?;:()'\"-]+$", message = "Content can only contain letters, numbers, spaces, and basic punctuation")
    private String content;
    private UUID postId;
    private UUID parentCommentId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public UUID getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(UUID parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public CommentRequestDTO(
            @NotBlank(message = "Content is required") @Pattern(regexp = "^[\\w\\s.,!?;:()'\"-]+$", message = "Content can only contain letters, numbers, spaces, and basic punctuation") String content,
            UUID postId, UUID parentCommentId) {
        this.content = content;
        this.postId = postId;
        this.parentCommentId = parentCommentId;
    }

    public CommentRequestDTO() {
    }

    @Override
    public String toString() {
        return "CommentRequestDTO [content=" + content + ", postId=" + postId + ", parentCommentId=" + parentCommentId
                + "]";
    }

}
