package ru.pnzgu.springblog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pnzgu.springblog.models.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
