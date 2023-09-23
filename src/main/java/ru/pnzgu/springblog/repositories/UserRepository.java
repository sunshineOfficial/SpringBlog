package ru.pnzgu.springblog.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.pnzgu.springblog.models.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
    List<UserEntity> findAllByRole_Id(Integer roleId, Pageable pageable);
    List<UserEntity> findAllByRole_Name(String name, Pageable pageable);
}
