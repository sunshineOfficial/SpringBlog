package ru.pnzgu.springblog.services;

import ru.pnzgu.springblog.dto.comment.*;
import ru.pnzgu.springblog.dto.common.PageDto;

/**
 * Интерфейс сервиса комментариев.
 */
public interface CommentService {
    /**
     * Создает новый комментарий. 
     *
     * @param request запрос на создание комментария 
     * @return идентификатор созданного комментария 
     */
    int create(CreateCommentRequest request);

    /**
     * Получает комментарий по указанному идентификатору. 
     *
     * @param id идентификатор комментария 
     * @return комментарий с указанным идентификатором 
     */
    GetCommentResponse getById(int id);

    /**
     * Получает все комментарии.
     * 
     * @param pageNumber номер страницы
     * @param pageSize   количество комментариев на странице
     * @return объект PageDto, содержащий комментарии
     */
    PageDto<GetCommentResponse> getAll(int pageNumber, int pageSize);

    /**
     * Получает все комментарии, отфильтрованные по флагу published.
     * 
     * @param pageNumber номер страницы
     * @param pageSize   количество комментариев на странице
     * @param published  флаг, указывающий, опубликован ли комментарий
     * @return объект PageDto, содержащий комментарии
     */
    PageDto<GetCommentResponse> getAllByPublished(int pageNumber, int pageSize, boolean published);

    /**
     * Получает все комментарии, отфильтрованные по идентификатору пользователя.
     *
     * @param pageNumber номер страницы
     * @param pageSize   количество комментариев на странице
     * @param userId     идентификатор пользователя, создавшего комментарий
     * @return объект PageDto, содержащий комментарии
     */
    PageDto<GetCommentResponse> getAllByUserId(int pageNumber, int pageSize, int userId);

    /**
     * Получает все комментарии, отфильтрованные по идентификатору пользователя и флагу published.
     *
     * @param pageNumber номер страницы
     * @param pageSize   количество комментариев на странице
     * @param userId     идентификатор пользователя, создавшего комментарий
     * @param published  флаг, указывающий, опубликован ли комментарий
     * @return объект PageDto, содержащий комментарии
     */
    PageDto<GetCommentResponse> getAllByUserIdAndPublished(int pageNumber, int pageSize, int userId, boolean published);

    /**
     * Получает все комментарии, отфильтрованные по идентификатору поста.
     *
     * @param pageNumber номер страницы
     * @param pageSize   количество комментариев на странице
     * @param postId     идентификатор поста, к которому относится комментарий
     * @return объект PageDto, содержащий комментарии
     */
    PageDto<GetCommentResponse> getAllByPostId(int pageNumber, int pageSize, int postId);

    /**
     * Получает все комментарии, отфильтрованные по идентификатору поста и флагу published.
     *
     * @param pageNumber номер страницы
     * @param pageSize   количество комментариев на странице
     * @param postId     идентификатор поста, к которому относится комментарий
     * @param published  флаг, указывающий, опубликован ли комментарий
     * @return объект PageDto, содержащий комментарии
     */
    PageDto<GetCommentResponse> getAllByPostIdAndPublished(int pageNumber, int pageSize, int postId, boolean published);

    /**
     * Получает все комментарии, отфильтрованные по идентификатору пользователя и идентификатору поста.
     *
     * @param pageNumber номер страницы
     * @param pageSize   количество комментариев на странице
     * @param userId     идентификатор пользователя, создавшего комментарий
     * @param postId     идентификатор поста, к которому относится комментарий
     * @return объект PageDto, содержащий комментарии
     */
    PageDto<GetCommentResponse> getAllByUserIdAndPostId(int pageNumber, int pageSize, int userId, int postId);

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
    PageDto<GetCommentResponse> getAllByUserIdAndPostIdAndPublished(int pageNumber, int pageSize, int userId, int postId, boolean published);

    /**
     * Изменяет содержимое комментария по заданному идентификатору. 
     *
     * @param id      идентификатор комментария 
     * @param request объект запроса на изменение содержимого комментария 
     * @return измененный комментарий
     */
    GetCommentResponse changeContent(int id, ChangeCommentContentRequest request);

    /**
     * Выполняет публикацию комментария с указанным идентификатором. 
     *
     * @param id идентификатор комментария 
     * @return опубликованный комментарий
     */
    GetCommentResponse publish(int id);

    /**
     * Удаляет комментарий по указанному идентификатору. 
     *
     * @param id идентификатор комментария
     */
    void delete(int id);
}
