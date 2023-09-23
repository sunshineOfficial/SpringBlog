package ru.pnzgu.springblog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pnzgu.springblog.models.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllByUser_Id(int userId);
    List<Post> findAllByPublished(boolean published);
    List<Post> findAllByUser_IdAndPublished(int userId, boolean published);
}
