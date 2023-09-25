package ru.pnzgu.springblog.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.pnzgu.springblog.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Page<Comment> findAllByPublished(boolean published, Pageable pageable);
    Page<Comment> findAllByUser_Id(int userId, Pageable pageable);
    Page<Comment> findAllByPost_Id(int postId, Pageable pageable);
    Page<Comment> findAllByUser_IdAndPublished(int userId, boolean published, Pageable pageable);
    Page<Comment> findAllByPost_IdAndPublished(int postId, boolean published, Pageable pageable);
    Page<Comment> findAllByUser_IdAndPost_Id(int userId, int postId, Pageable pageable);
    Page<Comment> findAllByUser_IdAndPost_IdAndPublished(int userId, int postId, boolean published, Pageable pageable);
}
