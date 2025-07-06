package io.github.angel.raa.controller;

import io.github.angel.raa.dto.request.post.PostDto;
import io.github.angel.raa.dto.request.post.PostUpdateDto;
import io.github.angel.raa.dto.response.PostResponseDTO;
import io.github.angel.raa.dto.response.Response;
import io.github.angel.raa.service.PostService;
import io.github.angel.raa.utils.ResponseUtils;
import jakarta.validation.Valid;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam(defaultValue = "10") int size) {
        PagedModel<PostResponseDTO> posts = postService.getAllPosts(PageRequest.of(page, size));
        System.out.println("Posts:   " + posts);
        return ResponseEntity.ok(posts);

    }

    // TODO: SOLUCIONA EN ERROR 00 Internal Server Error CON LAS SERIALIZACIONES
    // JSON
    @PreAuthorize("permitAll")
    @GetMapping(value = "/by-slug/{slug}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Response<PostResponseDTO>>> getPostBySlug(@PathVariable String slug) {
        System.out.println("Slug: " + slug);

        Response<PostResponseDTO> response = postService.getPostBySlug(slug);
        System.out.println("Post: " + response);
        System.out.println("Slug: " + slug);
        HttpStatus status = ResponseUtils.mapToHttpStatus(response);
        EntityModel<Response<PostResponseDTO>> resource = EntityModel.of(response);
        resource.add(
                org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo(
                        org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn(PostController.class)
                                .getAllPosts(0, 10))
                        .withRel("posts"));
        return ResponseEntity.status(status).body(resource);
    }

    // TODO: SOLUCIONA EN ERROR 00 Internal Server Error CON LAS SERIALIZACIONES
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER')")
    @GetMapping(value = "/{slug}/category")
    public ResponseEntity<Response<String>> addCategoryToPost(@PathVariable String slug,
            @RequestParam String categoryId) {
        System.out.println("Slug: " + slug);
        System.out.println("CategoryId: " + categoryId);
        Response<String> responseError = new Response<>();
        responseError.setMessage("Category ID cannot be null or empty");
        responseError.setData(null);

        if (categoryId == null || categoryId.isEmpty()) {
            return ResponseEntity.badRequest().body(responseError);
        }
        UUID categoryIdUuid = UUID.fromString(categoryId);
        System.out.println("CategoryId UUID: " + categoryIdUuid);
        Response<String> response = postService.addCategoryToPost(slug, categoryIdUuid);
        HttpStatus status = ResponseUtils.mapToHttpStatus(response);
        return ResponseEntity.status(status).body(response);
    }
    // TODO: SOLUCIONA EN ERROR 00 Internal Server Error CON LAS SERIALIZACIONES
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER')")
    @DeleteMapping(value = "/{slug}/category")
    public ResponseEntity<Response<String>> removeCategoryFromPost(@PathVariable String slug,
            @RequestParam String categoryId) {
        System.out.println("Slug: " + slug);
        System.out.println("CategoryId: " + categoryId);
        Response<String> responseError = new Response<>();
        responseError.setMessage("Category ID cannot be null or empty");
        responseError.setData(null);

        if (categoryId == null || categoryId.isEmpty()) {
            return ResponseEntity.badRequest().body(responseError);
        }
        UUID categoryIdUuid = UUID.fromString(categoryId);
        System.out.println("CategoryId UUID: " + categoryIdUuid);
        Response<String> response = postService.removeCategoryFromPost(slug, categoryIdUuid);
        HttpStatus status = ResponseUtils.mapToHttpStatus(response);
        return ResponseEntity.status(status).body(response);
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

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER')")
    @PutMapping(value = "/{slug}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<PostResponseDTO>> updatePost(@Valid @RequestBody PostUpdateDto postDto,
            @PathVariable String slug) {
        System.out.println("Datos recibidos para actualizar:");
        System.out.println("Title: " + postDto.title());
        System.out.println("Content: " + postDto.content());
        System.out.println("Status: " + postDto.status());
        Response<PostResponseDTO> response = postService.updatePost(postDto, slug);
        HttpStatus status = ResponseUtils.mapToHttpStatus(response);

        return ResponseEntity.status(status).body(response);
    }

}
