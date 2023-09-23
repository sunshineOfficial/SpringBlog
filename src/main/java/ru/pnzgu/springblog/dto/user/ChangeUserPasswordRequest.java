package ru.pnzgu.springblog.dto.user;

import lombok.Data;

@Data
public class ChangeUserPasswordRequest {
    private String oldPassword;
    private String newPassword;
}
