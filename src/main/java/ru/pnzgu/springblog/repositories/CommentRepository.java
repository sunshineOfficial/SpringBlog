package ru.pnzgu.springblog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pnzgu.springblog.models.Comment;
import ru.pnzgu.springblog.models.CommentKey;

public interface CommentRepository extends JpaRepository<Comment, CommentKey> {
}
