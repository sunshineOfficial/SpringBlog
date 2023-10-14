package ru.pnzgu.springblog.dto.role;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Класс, представляющий запрос на смену названия роли. 
 */
@Data
public class ChangeRoleNameRequest {
    /**
     * Название роли.
     * Не может быть пустым.
     */
    @NotBlank
    private String name;
}
