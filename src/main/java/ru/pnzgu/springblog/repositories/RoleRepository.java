package ru.pnzgu.springblog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pnzgu.springblog.models.Role;

import java.util.Optional;

/**
 * Репозиторий сущности Роль.
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
    /**
     * Находит роль по названию.
     * 
     * @param name название роли
     * @return роль
     */
    Optional<Role> findByName(String name);

    /**
     * Проверяет, существует ли роль с данным именем.
     * 
     * @param name название роли
     * @return true - роль существует, false - роль не существует.
     */
    boolean existsByName(String name);
}
