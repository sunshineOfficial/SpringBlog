package ru.pnzgu.springblog.services.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import ru.pnzgu.springblog.dto.common.PageDto;
import ru.pnzgu.springblog.dto.post.CreatePostRequest;
import ru.pnzgu.springblog.dto.post.GetPostResponse;
import ru.pnzgu.springblog.dto.post.UpdatePostRequest;
import ru.pnzgu.springblog.exceptions.AccessDeniedException;
import ru.pnzgu.springblog.exceptions.EntityNotFoundException;
import ru.pnzgu.springblog.exceptions.ValidationException;
import ru.pnzgu.springblog.helpers.AuthFacade;
import ru.pnzgu.springblog.helpers.PageDtoMaker;
import ru.pnzgu.springblog.models.Post;
import ru.pnzgu.springblog.repositories.PostRepository;
import ru.pnzgu.springblog.repositories.UserRepository;
import ru.pnzgu.springblog.services.PostService;

import java.util.Date;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final AuthFacade authFacade;
    private final UserRepository userRepository;
    private final PageDtoMaker<Post, GetPostResponse> pageDtoMaker;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, AuthFacade authFacade, UserRepository userRepository, PageDtoMaker<Post, GetPostResponse> pageDtoMaker) {
        this.postRepository = postRepository;
        this.authFacade = authFacade;
        this.userRepository = userRepository;
        this.pageDtoMaker = pageDtoMaker;
    }

    @Override
    public int create(CreatePostRequest request) {
        var username = authFacade.getAuth().getName();
        var user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
        var post = new Post(user, request.getTitle(), request.getContent());
        
        var newPost = postRepository.save(post);
        
        return newPost.getId();
    }

    @Override
    @Transactional
    public GetPostResponse getById(int id) {
        var post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found"));
        
        return mapToResponse(post);
    }

    @Override
    @Transactional
    public PageDto<GetPostResponse> getAll(int pageNumber, int pageSize) {
        var pageRequest = PageRequest.of(pageNumber, pageSize);
        var page = postRepository.findAll(pageRequest);
        
        return pageDtoMaker.makePageDto(page, this::mapToResponse);
    }

    @Override
    @Transactional
    public PageDto<GetPostResponse> getAllByPublished(int pageNumber, int pageSize, boolean published) {
        var pageRequest = PageRequest.of(pageNumber, pageSize);
        var page = postRepository.findAllByPublished(published, pageRequest);

        return pageDtoMaker.makePageDto(page, this::mapToResponse);
    }

    @Override
    @Transactional
    public PageDto<GetPostResponse> getAllByUserId(int pageNumber, int pageSize, int userId) {
        var pageRequest = PageRequest.of(pageNumber, pageSize);
        var page = postRepository.findAllByUser_Id(userId, pageRequest);

        return pageDtoMaker.makePageDto(page, this::mapToResponse);
    }

    @Override
    @Transactional
    public PageDto<GetPostResponse> getAllByUserIdAndPublished(int pageNumber, int pageSize, int userId, boolean published) {
        var pageRequest = PageRequest.of(pageNumber, pageSize);
        var page = postRepository.findAllByUser_IdAndPublished(userId, published, pageRequest);

        return pageDtoMaker.makePageDto(page, this::mapToResponse);
    }

    @Override
    @Transactional
    public GetPostResponse update(int id, UpdatePostRequest request) {
        var username = authFacade.getAuth().getName();
        var user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
        var post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found"));
        
        if (user.getId() != post.getUser().getId())
            throw new ValidationException("You must be the owner of the post");
        
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        
        var updatedPost = postRepository.save(post);
        
        return mapToResponse(updatedPost);
    }

    @Override
    @Transactional
    public GetPostResponse publish(int id) {
        var authorities = authFacade.getAuth().getAuthorities();
        
        if (!authorities.contains(new SimpleGrantedAuthority("ADMIN")) && 
                !authorities.contains(new SimpleGrantedAuthority("MODERATOR")))
            throw new AccessDeniedException("Access denied");
        
        var post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found"));
        
        if (post.isPublished())
            throw new ValidationException("Post already published");
        
        post.setPublished(true);
        post.setPublishedAt(new Date());

        var updatedPost = postRepository.save(post);

        return mapToResponse(updatedPost);
    }

    @Override
    public void delete(int id) {
        var authorities = authFacade.getAuth().getAuthorities();
        var username = authFacade.getAuth().getName();
        var currentUser = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
        var post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found"));

        if (!authorities.contains(new SimpleGrantedAuthority("ADMIN")) &&
                !authorities.contains(new SimpleGrantedAuthority("MODERATOR")) &&
                currentUser.getId() != post.getUser().getId())
            throw new AccessDeniedException("Access denied");
        
        postRepository.delete(post);
    }
    
    private GetPostResponse mapToResponse(Post post) {
        return new GetPostResponse(post.getId(), post.getUser().getId(), post.getTitle(), post.getContent(),
                post.isPublished(), post.getCreatedAt(), post.getUpdatedAt(), post.getPublishedAt());
    }
}
