package ru.pnzgu.springblog.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.pnzgu.springblog.models.UserEntity;

import java.util.Optional;

/**
 * Репозиторий сущности Пользователь.
 */
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    /**
     * Находит пользователя по логину.
     * 
     * @param username логин пользователя
     * @return пользователя
     */
    Optional<UserEntity> findByUsername(String username);

    /**
     * Проверяет, существует ли пользователь с данным логином.
     *
     * @param username логин пользователя
     * @return true - пользователь существует, false - пользователь не существует.
     */
    boolean existsByUsername(String username);

    /**
     * Находит всех пользователей, отфильтрованных по идентификатору роли.
     *
     * @param roleId   идентификатор роли
     * @param pageable объект Pageable
     * @return страницу пользователей
     */
    Page<UserEntity> findAllByRole_Id(Integer roleId, Pageable pageable);

    /**
     * Находит всех пользователей, отфильтрованных по названию роли.
     *
     * @param name     название роли
     * @param pageable объект Pageable
     * @return страницу пользователей
     */
    Page<UserEntity> findAllByRole_Name(String name, Pageable pageable);
}
