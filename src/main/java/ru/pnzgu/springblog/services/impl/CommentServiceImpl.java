package ru.pnzgu.springblog.services.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import ru.pnzgu.springblog.dto.comment.ChangeCommentContentRequest;
import ru.pnzgu.springblog.dto.comment.CreateCommentRequest;
import ru.pnzgu.springblog.dto.comment.GetCommentResponse;
import ru.pnzgu.springblog.dto.common.PageDto;
import ru.pnzgu.springblog.exceptions.AccessDeniedException;
import ru.pnzgu.springblog.exceptions.EntityNotFoundException;
import ru.pnzgu.springblog.exceptions.ValidationException;
import ru.pnzgu.springblog.helpers.AuthFacade;
import ru.pnzgu.springblog.helpers.PageDtoMaker;
import ru.pnzgu.springblog.models.Comment;
import ru.pnzgu.springblog.repositories.CommentRepository;
import ru.pnzgu.springblog.repositories.PostRepository;
import ru.pnzgu.springblog.repositories.UserRepository;
import ru.pnzgu.springblog.services.CommentService;

import java.util.Date;

/**
 * Класс сервиса комментариев.
 */
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final AuthFacade authFacade;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PageDtoMaker<Comment, GetCommentResponse> pageDtoMaker;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, AuthFacade authFacade, UserRepository userRepository, PostRepository postRepository, PageDtoMaker<Comment, GetCommentResponse> pageDtoMaker) {
        this.commentRepository = commentRepository;
        this.authFacade = authFacade;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.pageDtoMaker = pageDtoMaker;
    }

    /**
     * Создает новый комментарий.
     *
     * @param request запрос на создание комментария
     * @return идентификатор созданного комментария
     */
    @Override
    public int create(CreateCommentRequest request) {
        var username = authFacade.getAuth().getName();
        var user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
        var post = postRepository.findById(request.getPostId()).orElseThrow(() -> new EntityNotFoundException("Post not found"));
        var comment = new Comment(user, post, request.getContent());
        
        var newComment = commentRepository.save(comment);

        return newComment.getId();
    }

    /**
     * Получает комментарий по указанному идентификатору.
     *
     * @param id идентификатор комментария
     * @return комментарий с указанным идентификатором
     */
    @Override
    @Transactional
    public GetCommentResponse getById(int id) {
        var comment = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        
        return mapToResponse(comment);
    }

    /**
     * Получает все комментарии.
     *
     * @param pageNumber номер страницы
     * @param pageSize   количество комментариев на странице
     * @return объект PageDto, содержащий комментарии
     */
    @Override
    @Transactional
    public PageDto<GetCommentResponse> getAll(int pageNumber, int pageSize) {
        var pageRequest = PageRequest.of(pageNumber, pageSize);
        var page = commentRepository.findAll(pageRequest);
        
        return pageDtoMaker.makePageDto(page, this::mapToResponse);
    }

    /**
     * Получает все комментарии, отфильтрованные по флагу published.
     *
     * @param pageNumber номер страницы
     * @param pageSize   количество комментариев на странице
     * @param published  флаг, указывающий, опубликован ли комментарий
     * @return объект PageDto, содержащий комментарии
     */
    @Override
    @Transactional
    public PageDto<GetCommentResponse> getAllByPublished(int pageNumber, int pageSize, boolean published) {
        var pageRequest = PageRequest.of(pageNumber, pageSize);
        var page = commentRepository.findAllByPublished(published, pageRequest);

        return pageDtoMaker.makePageDto(page, this::mapToResponse);
    }

    /**
     * Получает все комментарии, отфильтрованные по идентификатору пользователя.
     *
     * @param pageNumber номер страницы
     * @param pageSize   количество комментариев на странице
     * @param userId     идентификатор пользователя, создавшего комментарий
     * @return объект PageDto, содержащий комментарии
     */
    @Override
    @Transactional
    public PageDto<GetCommentResponse> getAllByUserId(int pageNumber, int pageSize, int userId) {
        var pageRequest = PageRequest.of(pageNumber, pageSize);
        var page = commentRepository.findAllByUser_Id(userId, pageRequest);

        return pageDtoMaker.makePageDto(page, this::mapToResponse);
    }

    /**
     * Получает все комментарии, отфильтрованные по идентификатору пользователя и флагу published.
     *
     * @param pageNumber номер страницы
     * @param pageSize   количество комментариев на странице
     * @param userId     идентификатор пользователя, создавшего комментарий
     * @param published  флаг, указывающий, опубликован ли комментарий
     * @return объект PageDto, содержащий комментарии
     */
    @Override
    @Transactional
    public PageDto<GetCommentResponse> getAllByUserIdAndPublished(int pageNumber, int pageSize, int userId, boolean published) {
        var pageRequest = PageRequest.of(pageNumber, pageSize);
        var page = commentRepository.findAllByUser_IdAndPublished(userId, published, pageRequest);

        return pageDtoMaker.makePageDto(page, this::mapToResponse);
    }

    /**
     * Получает все комментарии, отфильтрованные по идентификатору поста.
     *
     * @param pageNumber номер страницы
     * @param pageSize   количество комментариев на странице
     * @param postId     идентификатор поста, к которому относится комментарий
     * @return объект PageDto, содержащий комментарии
     */
    @Override
    @Transactional
    public PageDto<GetCommentResponse> getAllByPostId(int pageNumber, int pageSize, int postId) {
        var pageRequest = PageRequest.of(pageNumber, pageSize);
        var page = commentRepository.findAllByPost_Id(postId, pageRequest);

        return pageDtoMaker.makePageDto(page, this::mapToResponse);
    }

    /**
     * Получает все комментарии, отфильтрованные по идентификатору поста и флагу published.
     *
     * @param pageNumber номер страницы
     * @param pageSize   количество комментариев на странице
     * @param postId     идентификатор поста, к которому относится комментарий
     * @param published  флаг, указывающий, опубликован ли комментарий
     * @return объект PageDto, содержащий комментарии
     */
    @Override
    @Transactional
    public PageDto<GetCommentResponse> getAllByPostIdAndPublished(int pageNumber, int pageSize, int postId, boolean published) {
        var pageRequest = PageRequest.of(pageNumber, pageSize);
        var page = commentRepository.findAllByPost_IdAndPublished(postId, published, pageRequest);

        return pageDtoMaker.makePageDto(page, this::mapToResponse);
    }

    /**
     * Получает все комментарии, отфильтрованные по идентификатору пользователя и идентификатору поста.
     *
     * @param pageNumber номер страницы
     * @param pageSize   количество комментариев на странице
     * @param userId     идентификатор пользователя, создавшего комментарий
     * @param postId     идентификатор поста, к которому относится комментарий
     * @return объект PageDto, содержащий комментарии
     */
    @Override
    @Transactional
    public PageDto<GetCommentResponse> getAllByUserIdAndPostId(int pageNumber, int pageSize, int userId, int postId) {
        var pageRequest = PageRequest.of(pageNumber, pageSize);
        var page = commentRepository.findAllByUser_IdAndPost_Id(userId, postId, pageRequest);

        return pageDtoMaker.makePageDto(page, this::mapToResponse);
    }

    /**
     * Получает все комментарии, отфильтрованные по идентификатору пользователя, идентификатору поста и флагу published.
     *
     * @param pageNumber номер страницы
     * @param pageSize   количество комментариев на странице
     * @param userId     идентификатор пользователя, создавшего комментарий
     * @param postId     идентификатор поста, к которому относится комментарий
     * @param published  флаг, указывающий, опубликован ли комментарий
     * @return объект PageDto, содержащий комментарии
     */
    @Override
    @Transactional
    public PageDto<GetCommentResponse> getAllByUserIdAndPostIdAndPublished(int pageNumber, int pageSize, int userId, int postId, boolean published) {
        var pageRequest = PageRequest.of(pageNumber, pageSize);
        var page = commentRepository.findAllByUser_IdAndPost_IdAndPublished(userId, postId, published, pageRequest);

        return pageDtoMaker.makePageDto(page, this::mapToResponse);
    }

    /**
     * Изменяет содержимое комментария по заданному идентификатору.
     *
     * @param id      идентификатор комментария
     * @param request объект запроса на изменение содержимого комментария
     * @return измененный комментарий
     */
    @Override
    @Transactional
    public GetCommentResponse changeContent(int id, ChangeCommentContentRequest request) {
        var username = authFacade.getAuth().getName();
        var user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
        var comment = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        
        if (user.getId() != comment.getUser().getId())
            throw new ValidationException("You must be the owner of the comment");
        
        comment.setContent(request.getContent());
        
        var updatedComment = commentRepository.save(comment);

        return mapToResponse(updatedComment);
    }

    /**
     * Выполняет публикацию комментария с указанным идентификатором.
     *
     * @param id идентификатор комментария
     * @return опубликованный комментарий
     */
    @Override
    @Transactional
    public GetCommentResponse publish(int id) {
        var authorities = authFacade.getAuth().getAuthorities();

        if (!authorities.contains(new SimpleGrantedAuthority("ADMIN")) &&
                !authorities.contains(new SimpleGrantedAuthority("MODERATOR")))
            throw new AccessDeniedException("Access denied");
        
        var comment = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        
        if (comment.isPublished())
            throw new ValidationException("Comment already published");
        
        comment.setPublished(true);
        comment.setPublishedAt(new Date());
        
        var updatedComment = commentRepository.save(comment);
        
        return mapToResponse(updatedComment);
    }

    /**
     * Удаляет комментарий по указанному идентификатору.
     *
     * @param id идентификатор комментария
     */
    @Override
    public void delete(int id) {
        var comment = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        var authorities = authFacade.getAuth().getAuthorities();
        var username = authFacade.getAuth().getName();
        var currentUser = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (!authorities.contains(new SimpleGrantedAuthority("ADMIN")) &&
                !authorities.contains(new SimpleGrantedAuthority("MODERATOR")) &&
                currentUser.getId() != comment.getUser().getId())
            throw new AccessDeniedException("Access denied");

        commentRepository.delete(comment);
    }

    /**
     * Преобразует Comment в GetCommentResponse.
     * 
     * @param comment объект Comment, который будет преобразован
     * @return объект GetCommentResponse, содержащий данные из объекта Comment
     */
    private GetCommentResponse mapToResponse(Comment comment) {
        return new GetCommentResponse(comment.getId(), comment.getUser().getId(), comment.getPost().getId(),
                comment.getContent(), comment.isPublished(), comment.getCreatedAt(), comment.getUpdatedAt(),
                comment.getPublishedAt());
    }
}
