package io.github.angel.raa.service;

import java.util.List;
import java.util.UUID;

import io.github.angel.raa.dto.request.comment.CommentRequestDTO;
import io.github.angel.raa.dto.response.CommentResponseDTO;
import io.github.angel.raa.dto.response.Response;

/**
 * Service interface for managing comments on posts.
 * This interface defines methods for creating, updating, deleting, and retrieving comments.
 * It is designed to be implemented by a class that provides the actual business logic for comment management.
 */
public interface CommentService {
    Response<CommentResponseDTO> createComment(CommentRequestDTO commentRequestDto);

    Response<CommentResponseDTO> updateComment(UUID commentId, CommentRequestDTO commentRequestDto);

    Response<String> deleteComment(UUID commentId);

    Response<CommentResponseDTO> getCommentById(UUID commentId);

    Response<List<CommentResponseDTO>> getCommentsByPostId(UUID postId);

    Response<List<CommentResponseDTO>> getReplies(UUID parentCommentId);
    

}
