package ru.pnzgu.springblog.services;

import ru.pnzgu.springblog.dto.common.PageDto;
import ru.pnzgu.springblog.dto.post.CreatePostRequest;
import ru.pnzgu.springblog.dto.post.GetPostResponse;
import ru.pnzgu.springblog.dto.post.UpdatePostRequest;

import java.io.IOException;

/**
 * Интерфейс сервиса постов.
 */
public interface PostService {
    /**
     * Создает новый пост. 
     *
     * @param request запрос на создание поста 
     * @return идентификатор созданного поста 
     */
    int create(CreatePostRequest request) throws IOException;

    /**
     * Получает пост по указанному идентификатору. 
     *
     * @param id идентификатор поста 
     * @return пост с указанным идентификатором 
     */
    GetPostResponse getById(int id);

    /**
     * Получает изображение поста.
     * 
     * @param postId идентификатор поста
     * @return изображение поста
     */
    byte[] getPostImage(int postId);

    /**
     * Получает все посты.
     *
     * @param pageNumber номер страницы
     * @param pageSize   количество постов на странице
     * @return объект PageDto, содержащий посты
     */
    PageDto<GetPostResponse> getAll(int pageNumber, int pageSize);

    /**
     * Получает все посты, отфильтрованные по флагу published.
     *
     * @param pageNumber номер страницы
     * @param pageSize   количество постов на странице
     * @param published  флаг, указывающий, опубликован ли пост
     * @return объект PageDto, содержащий посты
     */
    PageDto<GetPostResponse> getAllByPublished(int pageNumber, int pageSize, boolean published);

    /**
     * Получает все посты, отфильтрованные по идентификатору пользователя.
     *
     * @param pageNumber номер страницы
     * @param pageSize   количество постов на странице
     * @param userId     идентификатор пользователя, создавшего пост
     * @return объект PageDto, содержащий посты
     */
    PageDto<GetPostResponse> getAllByUserId(int pageNumber, int pageSize, int userId);

    /**
     * Получает все посты, отфильтрованные по идентификатору пользователя и флагу published.
     *
     * @param pageNumber номер страницы
     * @param pageSize   количество постов на странице
     * @param userId     идентификатор пользователя, создавшего пост
     * @param published  флаг, указывающий, опубликован ли пост
     * @return объект PageDto, содержащий посты
     */
    PageDto<GetPostResponse> getAllByUserIdAndPublished(int pageNumber, int pageSize, int userId, boolean published);

    /**
     * Обновляет пост по заданному идентификатору. 
     *
     * @param id      идентификатор поста 
     * @param request объект запроса на обновление поста
     * @return обновленный пост
     */
    GetPostResponse update(int id, UpdatePostRequest request);

    /**
     * Выполняет публикацию поста с указанным идентификатором. 
     *
     * @param id идентификатор поста 
     * @return опубликованный пост
     */
    GetPostResponse publish(int id);

    /**
     * Удаляет пост по указанному идентификатору. 
     *
     * @param id идентификатор поста
     */
    void delete(int id);
}
