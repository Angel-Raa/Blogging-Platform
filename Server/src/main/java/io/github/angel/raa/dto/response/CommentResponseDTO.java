package io.github.angel.raa.dto.response;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommentResponseDTO {
    private UUID commentId;
    private UUID postId;
    private UUID authorId;
    private String authorName;
    private String content;
    private String createdAt;
    private String updatedAt;
    private UUID parentCommentId;
    private List<CommentResponseDTO> replies = new ArrayList<>();

    public CommentResponseDTO() {
    }

    public CommentResponseDTO(UUID commentId, UUID postId, UUID authorId, String authorName, String content,
            String createdAt, String updatedAt, UUID parentCommentId, List<CommentResponseDTO> replies) {
        this.commentId = commentId;
        this.postId = postId;
        this.authorId = authorId;
        this.authorName = authorName;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.parentCommentId = parentCommentId;
        this.replies = replies;
    }

    public UUID getCommentId() {
        return commentId;
    }

    public void setCommentId(UUID commentId) {
        this.commentId = commentId;
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public UUID getAuthorId() {
        return authorId;
    }

    public void setAuthorId(UUID authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UUID getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(UUID parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public List<CommentResponseDTO> getReplies() {
        return replies;
    }

    public void setReplies(List<CommentResponseDTO> replies) {
        this.replies = replies;
    }

    @Override
    public String toString() {
        return "CommentResponseDTO [commentId=" + commentId + ", postId=" + postId + ", authorId=" + authorId
                + ", authorName=" + authorName + ", content=" + content + ", createdAt=" + createdAt + ", updatedAt="
                + updatedAt + ", parentCommentId=" + parentCommentId + ", replies=" + replies + "]";
    }

}
