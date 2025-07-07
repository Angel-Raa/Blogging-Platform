package io.github.angel.raa.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import io.github.angel.raa.dto.request.comment.CommentRequestDTO;
import io.github.angel.raa.dto.response.CommentResponseDTO;
import io.github.angel.raa.dto.response.Response;
import io.github.angel.raa.persistence.repository.CommentRepository;
import io.github.angel.raa.persistence.repository.PostRepository;
import io.github.angel.raa.service.AuthenticationService;
import io.github.angel.raa.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
    private final AuthenticationService authenticationService;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public CommentServiceImpl(AuthenticationService authenticationService, PostRepository postRepository,
            CommentRepository commentRepository) {
        this.authenticationService = authenticationService;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Response<CommentResponseDTO> createComment(CommentRequestDTO commentRequestDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createComment'");
    }

    @Override
    public Response<CommentResponseDTO> updateComment(UUID commentId, CommentRequestDTO commentRequestDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateComment'");
    }

    @Override
    public Response<String> deleteComment(UUID commentId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteComment'");
    }

    @Override
    public Response<CommentResponseDTO> getCommentById(UUID commentId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCommentById'");
    }

    @Override
    public Response<List<CommentResponseDTO>> getCommentsByPostId(UUID postId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCommentsByPostId'");
    }

    @Override
    public Response<List<CommentResponseDTO>> getReplies(UUID parentCommentId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getReplies'");
    }

}
