package ru.pnzgu.springblog.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.pnzgu.springblog.models.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
    Page<Post> findAllByUser_Id(int userId, Pageable pageable);
    Page<Post> findAllByPublished(boolean published, Pageable pageable);
    Page<Post> findAllByUser_IdAndPublished(int userId, boolean published, Pageable pageable);
}
