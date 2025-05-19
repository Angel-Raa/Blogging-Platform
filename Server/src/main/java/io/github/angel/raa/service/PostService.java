package io.github.angel.raa.service;

import io.github.angel.raa.dto.request.post.PostDto;
import io.github.angel.raa.dto.response.PostResponseDTO;
import io.github.angel.raa.dto.response.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PostService {
    Response<PostResponseDTO> createPost(PostDto postDto);
    Response<PostResponseDTO> updatePost(PostDto postDto, String slug);
    Response<PostResponseDTO> getPostBySlug(String slug);
    Page<PostResponseDTO> getAllPosts(Pageable pageable);
    Response<String> deletePost(String slug);
    Response<String> addCategoryToPost(String slug, Long categoryId);
    Response<String> removeCategoryFromPost(String slug, Long categoryId);






}
