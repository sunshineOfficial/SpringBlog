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
import ru.pnzgu.springblog.helpers.ImageUtils;
import ru.pnzgu.springblog.helpers.PageDtoMaker;
import ru.pnzgu.springblog.models.Image;
import ru.pnzgu.springblog.models.Post;
import ru.pnzgu.springblog.repositories.PostRepository;
import ru.pnzgu.springblog.repositories.UserRepository;
import ru.pnzgu.springblog.services.PostService;

import java.io.IOException;
import java.util.Date;

/**
 * Класс сервиса постов.
 */
@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final AuthFacade authFacade;
    private final UserRepository userRepository;
    private final PageDtoMaker<Post, GetPostResponse> pageDtoMaker;
    private final ImageUtils imageUtils;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, AuthFacade authFacade, UserRepository userRepository, PageDtoMaker<Post, GetPostResponse> pageDtoMaker, ImageUtils imageUtils) {
        this.postRepository = postRepository;
        this.authFacade = authFacade;
        this.userRepository = userRepository;
        this.pageDtoMaker = pageDtoMaker;
        this.imageUtils = imageUtils;
    }

    /**
     * Создает новый пост. 
     *
     * @param request запрос на создание поста 
     * @return идентификатор созданного поста 
     */
    @Override
    public int create(CreatePostRequest request) throws IOException {
        var username = authFacade.getAuth().getName();
        var user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
        var file = request.getImage();
        var image = new Image(file.getOriginalFilename(), file.getContentType(), imageUtils.compress(file.getBytes()));
        var post = new Post(user, image, request.getTitle(), request.getContent());
        
        var newPost = postRepository.save(post);
        
        return newPost.getId();
    }

    /**
     * Получает пост по указанному идентификатору. 
     *
     * @param id идентификатор поста 
     * @return пост с указанным идентификатором 
     */
    @Override
    @Transactional
    public GetPostResponse getById(int id) {
        var post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found"));
        
        return mapToResponse(post);
    }

    /**
     * Получает изображение поста.
     *
     * @param postId идентификатор поста
     * @return изображение поста
     */
    @Override
    @Transactional
    public byte[] getPostImage(int postId) {
        var post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found"));
        var image = post.getImage();
        
        return imageUtils.decompress(image.getData());
    }

    /**
     * Получает все посты.
     *
     * @param pageNumber номер страницы
     * @param pageSize   количество постов на странице
     * @return объект PageDto, содержащий посты
     */
    @Override
    @Transactional
    public PageDto<GetPostResponse> getAll(int pageNumber, int pageSize) {
        var pageRequest = PageRequest.of(pageNumber, pageSize);
        var page = postRepository.findAll(pageRequest);
        
        return pageDtoMaker.makePageDto(page, this::mapToResponse);
    }

    /**
     * Получает все посты, отфильтрованные по флагу published.
     *
     * @param pageNumber номер страницы
     * @param pageSize   количество постов на странице
     * @param published  флаг, указывающий, опубликован ли пост
     * @return объект PageDto, содержащий посты
     */
    @Override
    @Transactional
    public PageDto<GetPostResponse> getAllByPublished(int pageNumber, int pageSize, boolean published) {
        var pageRequest = PageRequest.of(pageNumber, pageSize);
        var page = postRepository.findAllByPublished(published, pageRequest);

        return pageDtoMaker.makePageDto(page, this::mapToResponse);
    }

    /**
     * Получает все посты, отфильтрованные по идентификатору пользователя.
     *
     * @param pageNumber номер страницы
     * @param pageSize   количество постов на странице
     * @param userId     идентификатор пользователя, создавшего пост
     * @return объект PageDto, содержащий посты
     */
    @Override
    @Transactional
    public PageDto<GetPostResponse> getAllByUserId(int pageNumber, int pageSize, int userId) {
        var pageRequest = PageRequest.of(pageNumber, pageSize);
        var page = postRepository.findAllByUser_Id(userId, pageRequest);

        return pageDtoMaker.makePageDto(page, this::mapToResponse);
    }

    /**
     * Получает все посты, отфильтрованные по идентификатору пользователя и флагу published.
     *
     * @param pageNumber номер страницы
     * @param pageSize   количество постов на странице
     * @param userId     идентификатор пользователя, создавшего пост
     * @param published  флаг, указывающий, опубликован ли пост
     * @return объект PageDto, содержащий посты
     */
    @Override
    @Transactional
    public PageDto<GetPostResponse> getAllByUserIdAndPublished(int pageNumber, int pageSize, int userId, boolean published) {
        var pageRequest = PageRequest.of(pageNumber, pageSize);
        var page = postRepository.findAllByUser_IdAndPublished(userId, published, pageRequest);

        return pageDtoMaker.makePageDto(page, this::mapToResponse);
    }

    /**
     * Обновляет пост по заданному идентификатору. 
     *
     * @param id      идентификатор поста 
     * @param request объект запроса на обновление поста
     * @return обновленный пост
     */
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

    /**
     * Выполняет публикацию поста с указанным идентификатором. 
     *
     * @param id идентификатор поста 
     * @return опубликованный пост
     */
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

    /**
     * Удаляет пост по указанному идентификатору. 
     *
     * @param id идентификатор поста
     */
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

    /**
     * Преобразует Post в GetPostResponse.
     *
     * @param post объект Post, который будет преобразован
     * @return объект GetPostResponse, содержащий данные из объекта Post
     */
    private GetPostResponse mapToResponse(Post post) {
        return new GetPostResponse(post.getId(), post.getUser().getId(), post.getTitle(), post.getContent(),
                post.isPublished(), post.getCreatedAt(), post.getUpdatedAt(), post.getPublishedAt());
    }
}
