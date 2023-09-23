package ru.pnzgu.springblog.services;

import ru.pnzgu.springblog.dto.common.PageDto;
import ru.pnzgu.springblog.dto.user.GetUserResponse;

public interface UserService {
    GetUserResponse getById(int id);
    PageDto<GetUserResponse> getAll(int pageNumber, int pageSize);
    PageDto<GetUserResponse> getAllByRoleId(int pageNumber, int pageSize, int roleId);
    PageDto<GetUserResponse> getAllByRoleName(int pageNumber, int pageSize, String name);
    GetUserResponse changePassword(String newPassword);
    GetUserResponse changeRole(int roleId);
    void delete(int id);
}
