package ru.pnzgu.springblog.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.pnzgu.springblog.models.Comment;

/**
 * Репозиторий сущности Комментарий.
 */
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    /**
     * Находит все комментарии, отфильтрованные по флагу published.
     * 
     * @param published флаг, указывающий, опубликован ли комментарий
     * @param pageable  объект Pageable
     * @return страницу комментариев
     */
    Page<Comment> findAllByPublished(boolean published, Pageable pageable);

    /**
     * Находит все комментарии, отфильтрованные по идентификатору пользователя.
     *
     * @param userId   идентификатор пользователя, создавшего комментарий
     * @param pageable объект Pageable
     * @return страницу комментариев
     */
    Page<Comment> findAllByUser_Id(int userId, Pageable pageable);

    /**
     * Находит все комментарии, отфильтрованные по идентификатору поста.
     *
     * @param postId   идентификатор поста, к которому относится комментарий
     * @param pageable объект Pageable
     * @return страницу комментариев
     */
    Page<Comment> findAllByPost_Id(int postId, Pageable pageable);

    /**
     * Находит все комментарии, отфильтрованные по идентификатору пользователя и флагу published.
     *
     * @param userId    идентификатор пользователя, создавшего комментарий
     * @param published флаг, указывающий, опубликован ли комментарий
     * @param pageable  объект Pageable
     * @return страницу комментариев
     */
    Page<Comment> findAllByUser_IdAndPublished(int userId, boolean published, Pageable pageable);

    /**
     * Находит все комментарии, отфильтрованные по идентификатору поста и флагу published.
     *
     * @param postId    идентификатор поста, к которому относится комментарий
     * @param published флаг, указывающий, опубликован ли комментарий
     * @param pageable  объект Pageable
     * @return страницу комментариев
     */
    Page<Comment> findAllByPost_IdAndPublished(int postId, boolean published, Pageable pageable);

    /**
     * Находит все комментарии, отфильтрованные по идентификатору пользователя и идентификатору поста.
     *
     * @param userId   идентификатор пользователя, создавшего комментарий
     * @param postId   идентификатор поста, к которому относится комментарий
     * @param pageable объект Pageable
     * @return страницу комментариев
     */
    Page<Comment> findAllByUser_IdAndPost_Id(int userId, int postId, Pageable pageable);

    /**
     * Находит все комментарии, отфильтрованные по идентификатору пользователя, идентификатору поста и флагу published.
     *
     * @param userId    идентификатор пользователя, создавшего комментарий
     * @param postId    идентификатор поста, к которому относится комментарий
     * @param published флаг, указывающий, опубликован ли комментарий
     * @param pageable  объект Pageable
     * @return страницу комментариев
     */
    Page<Comment> findAllByUser_IdAndPost_IdAndPublished(int userId, int postId, boolean published, Pageable pageable);
}
