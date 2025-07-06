package io.github.angel.raa.service;

import io.github.angel.raa.dto.request.post.PostDto;
import io.github.angel.raa.dto.request.post.PostUpdateDto;
import io.github.angel.raa.dto.response.PostResponseDTO;
import io.github.angel.raa.dto.response.Response;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;


public interface PostService {
    Response<PostResponseDTO> createPost(PostDto postDto);
    Response<PostResponseDTO> updatePost(PostUpdateDto postDto, String slug);
    Response<PostResponseDTO> getPostBySlug(String slug);
    PagedModel<PostResponseDTO> getAllPosts(Pageable pageable);
    Response<String> deletePost(String slug);
    Response<String> addCategoryToPost(String slug, UUID categoryId);
    Response<String> removeCategoryFromPost(String slug, UUID categoryId);






}
