package ru.pnzgu.springblog.dto.role;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Класс, представляющий запрос на создание роли. 
 */
@Data
public class CreateRoleRequest {
    /**
     * Название роли.
     * Не может быть пустым.
     */
    @NotBlank
    private String name;
}
