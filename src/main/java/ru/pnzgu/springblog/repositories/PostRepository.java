package ru.pnzgu.springblog.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.pnzgu.springblog.models.Post;

/**
 * Репозиторий сущности Пост.
 */
public interface PostRepository extends JpaRepository<Post, Integer> {
    /**
     * Находит все посты, отфильтрованные по идентификатору пользователя.
     *
     * @param userId   идентификатор пользователя, создавшего пост
     * @param pageable объект Pageable
     * @return страницу постов
     */
    Page<Post> findAllByUser_Id(int userId, Pageable pageable);

    /**
     * Находит все посты, отфильтрованные по флагу published.
     *
     * @param published флаг, указывающий, опубликован ли пост
     * @param pageable  объект Pageable
     * @return страницу постов
     */
    Page<Post> findAllByPublished(boolean published, Pageable pageable);

    /**
     * Находит все посты, отфильтрованные по идентификатору пользователя и флагу published.
     *
     * @param userId    идентификатор пользователя, создавшего пост
     * @param published флаг, указывающий, опубликован ли пост
     * @param pageable объект Pageable
     * @return страницу постов
     */
    Page<Post> findAllByUser_IdAndPublished(int userId, boolean published, Pageable pageable);
}
