package ru.pnzgu.springblog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pnzgu.springblog.models.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
    boolean existsByName(String name);
}
