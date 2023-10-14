package ru.pnzgu.springblog.dto.user;

import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * Класс, представляющий запрос на смену роли пользователя. 
 */
@Data
public class ChangeRoleRequest {
    /**
     * Идентификатор роли. 
     * Не может быть менее 1.
     */
    @Min(1)
    private int roleId;
}
