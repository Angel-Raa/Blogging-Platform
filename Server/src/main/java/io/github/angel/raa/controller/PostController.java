package io.github.angel.raa.controller;

import io.github.angel.raa.dto.request.post.PostDto;
import io.github.angel.raa.dto.response.PostResponseDTO;
import io.github.angel.raa.dto.response.Response;
import io.github.angel.raa.service.PostService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@Validated
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    @PreAuthorize("permitAll")
    @GetMapping
    public ResponseEntity<PagedModel<PostResponseDTO>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PagedModel<PostResponseDTO> posts = postService.getAllPosts(PageRequest.of(page, size));
        System.out.println("Posts:   " + posts);
        return ResponseEntity.ok(posts);

    }
    @GetMapping("/{slug}")
    public ResponseEntity<PostResponseDTO> getPostBySlug(@PathVariable @Valid String slug) {
        if (slug == null || slug.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        PostResponseDTO post = postService.getPostBySlug(slug).getData();
        return ResponseEntity.ok(post);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER')")
    @PostMapping
    public ResponseEntity<Response<PostResponseDTO>> createPost(@Valid @RequestBody PostDto postDto) {

        System.out.println("Datos recibidos:");
        System.out.println("Title: " + postDto.title());
        System.out.println("Content: " + postDto.content());
        System.out.println("Status: " + postDto.status());
        System.out.println("CategoryId: " + postDto.categoryId());
        Response<PostResponseDTO> response = postService.createPost(postDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



}
