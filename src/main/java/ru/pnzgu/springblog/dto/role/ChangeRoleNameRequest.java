package ru.pnzgu.springblog.dto.role;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangeRoleNameRequest {
    @NotBlank
    private String name;
}
