package ru.pnzgu.springblog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pnzgu.springblog.models.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
}
