package io.github.angel.raa.service.impl;

import io.github.angel.raa.dto.request.post.PostDto;
import io.github.angel.raa.dto.request.post.PostUpdateDto;
import io.github.angel.raa.dto.response.CategoryResponse;
import io.github.angel.raa.dto.response.PostResponseDTO;
import io.github.angel.raa.dto.response.Response;
import io.github.angel.raa.exception.DuplicateSlugException;
import io.github.angel.raa.exception.DuplicateTitleException;
import io.github.angel.raa.exception.ResourceNotFoundException;
import io.github.angel.raa.exception.SlugNotFoundException;
import io.github.angel.raa.exception.UsernameNotFoundException;
import io.github.angel.raa.persistence.entity.Category;
import io.github.angel.raa.persistence.entity.Post;
import io.github.angel.raa.persistence.entity.User;
import io.github.angel.raa.persistence.repository.CategoryRepository;
import io.github.angel.raa.persistence.repository.PostRepository;
import io.github.angel.raa.persistence.repository.UserRepository;
import io.github.angel.raa.service.AuthenticationService;
import io.github.angel.raa.service.PostService;
import io.github.angel.raa.utils.Slugify;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.time.LocalDateTime.now;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final AuthenticationService authenticationService;
    private final CategoryRepository categoryRepository;

    public PostServiceImpl(UserRepository userRepository, PostRepository postRepository,
            AuthenticationService authenticationService, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.authenticationService = authenticationService;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    @Override
    public Response<PostResponseDTO> createPost(@NotNull PostDto postDto) {
        validateUniqueTitleAndSlug(postDto.title(), Slugify.slugify(postDto.title()));
        UUID authorId = authenticationService.getCurrentUserId();
        User user = userRepository.findById(authorId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Post post = mapDtoToPost(postDto);
        post.setAuthorId(authorId);
        post.setAuthor(user);
        System.out.println("User en Post " + authenticationService.getCurrentUserId());
        if (postDto.categoryId() != null) {
            Category category = categoryRepository.findById(postDto.categoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found"));
            post.setCategories(Set.of(category)); // Asocia la categoría
        }
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        Post postSave = postRepository.save(post);
        System.out.println("Post saved " + postSave);
        PostResponseDTO dto = mapEntityToDto(postSave);
        System.out.println("DTO:  " + dto);
        return Response.<PostResponseDTO>builder()
                .message("Post created successfully")
                .success(true)
                .code(201)
                .data(dto)
                .timestamp(LocalDateTime.now())
                .buildResponse();

    }

    @Transactional
    @Override
    public Response<PostResponseDTO> updatePost(PostUpdateDto postDto, String slug) {
        Post post = postRepository.findBySlug(slug)
                .orElseThrow(() -> new SlugNotFoundException("Post not found"));
        post.setTitle(postDto.title());
        post.setSlug(Slugify.slugify(postDto.title()));
        post.setContent(postDto.content());
        post.setStatus(postDto.status());
        post.setUpdatedAt(LocalDateTime.now());

        Post updatedPost = postRepository.save(post);
        PostResponseDTO dto = mapEntityToDto(updatedPost);
        return Response.<PostResponseDTO>builder()
                .message("Post updated successfully")
                .success(true)
                .code(200)
                .data(dto)
                .timestamp(LocalDateTime.now())
                .buildResponse();
    }

    @Transactional(readOnly = true)
    @Override
    public Response<PostResponseDTO> getPostBySlug(String slug) {
        Post post = postRepository.findBySlug(slug).orElseThrow(() -> new SlugNotFoundException("Post not found"));

        return Response.<PostResponseDTO>builder()
                .message("Post retrieved successfully")
                .success(true)
                .code(200)
                .data(PostResponseDTO.fromPost(post))
                .timestamp(LocalDateTime.now())
                .buildResponse();
    }

    @Transactional(readOnly = true)
    @Override
    public PagedModel<PostResponseDTO> getAllPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        Page<PostResponseDTO> dtoPage = posts.map(PostResponseDTO::fromPost);
        return PagedModel.of(dtoPage.getContent(),
                new PagedModel.PageMetadata(dtoPage.getSize(), dtoPage.getNumber(), dtoPage.getTotalElements(),
                        dtoPage.getTotalPages()));
    }

    @Override
    public Response<String> deletePost(String slug) {
        return null;
    }

    @Transactional
    @Override
    public Response<String> addCategoryToPost(String slug, UUID categoryId) {
        Post post = postRepository.findBySlug(slug)
                .orElseThrow(() -> new SlugNotFoundException("Post not found"));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        post.getCategories().add(category);
        postRepository.save(post);
        // Recarga el post para asegurar que las relaciones se cargan correctament
        


        return Response.<String>builder()
                .message("Category added to post successfully")
                .success(true)
                .code(200)
                .data("Category added")
                .timestamp(now())
                .buildResponse();
    }



    @Transactional
    @Override
    public Response<String> removeCategoryFromPost(String slug, UUID categoryId) {
        Post post = postRepository.findBySlug(slug)
                .orElseThrow(() -> new SlugNotFoundException("Post not found"));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        post.getCategories().remove(category);
        postRepository.save(post);
        return Response.<String>builder()
                .message("Category removed from post successfully")
                .success(true)
                .code(200)
                .data("Category removed")
                .buildResponse();
    }

    
    private Post mapDtoToPost(@NotNull PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.title());
        post.setSlug(Slugify.slugify(postDto.title()));
        post.setContent(postDto.content());
        post.setStatus(postDto.status());
        post.setPublishedAt(LocalDateTime.now(ZoneId.of("UTC")));
        post.setPublishedAt(LocalDateTime.now());
        post.setCategoryId(postDto.categoryId());

        return post;

    }

    private void validateUniqueTitle(String title) {
        if (postRepository.existsByTitle(title)) {
            throw new DuplicateTitleException("Title already exists: " + title);
        }
    }

    private void validateUniqueSlug(String slug) {
        if (postRepository.existsBySlug(slug)) {
            throw new DuplicateSlugException("Slug already exists: " + slug);
        }
    }

    private void validateUniqueTitleAndSlug(String title, String slug) {
        validateUniqueTitle(title);
        validateUniqueSlug(slug);
    }

    private PostResponseDTO mapEntityToDto(Post save) {
        PostResponseDTO dto = new PostResponseDTO();
        dto.setPostId(save.getPostId());
        dto.setTitle(save.getTitle());
        dto.setSlug(save.getSlug());
        dto.setContent(save.getContent());
        dto.setStatus(save.getStatus());
        dto.setPublishedAt(save.getPublishedAt());
        dto.setCreatedAt(save.getCreatedAt());
        dto.setUpdatedAt(save.getUpdatedAt());
        dto.setAuthorId(save.getAuthorId());
        dto.setAuthorName(save.getAuthor() != null ? save.getAuthor().getUsername() : null);
        dto.setCategories(save.getCategories() != null
            ? save.getCategories().stream()
                .map(category -> new CategoryResponse(
                    category.getName(),
                    category.getSlug(),
                    category.getCategoryId()))
                .collect(Collectors.toSet())
            : new HashSet<>());
        return dto;
    }

}
