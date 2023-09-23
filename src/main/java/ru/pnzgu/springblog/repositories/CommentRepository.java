package ru.pnzgu.springblog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pnzgu.springblog.models.Comment;
import ru.pnzgu.springblog.models.CommentKey;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, CommentKey> {
    List<Comment> findAllByPublished(boolean published);
    List<Comment> findAllByUser_Id(int userId);
    List<Comment> findAllByPost_Id(int postId);
    List<Comment> findAllByUser_IdAndPublished(int userId, boolean published);
    List<Comment> findAllByPost_IdAndPublished(int postId, boolean published);
}
