package ru.pnzgu.springblog.dto.user;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class ChangeRoleRequest {
    @Min(1)
    private int roleId;
}
