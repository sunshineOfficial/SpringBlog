package ru.pnzgu.springblog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pnzgu.springblog.models.Image;

import java.util.Optional;

/**
 * Репозиторий сущности Изображение.
 */
public interface ImageRepository extends JpaRepository<Image, Integer> {
}
