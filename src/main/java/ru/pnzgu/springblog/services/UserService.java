package ru.pnzgu.springblog.services;

import ru.pnzgu.springblog.dto.common.PageDto;
import ru.pnzgu.springblog.dto.user.ChangeUserPasswordRequest;
import ru.pnzgu.springblog.dto.user.GetUserResponse;

public interface UserService {
    GetUserResponse getById(int id);
    GetUserResponse getByUsername(String username);
    PageDto<GetUserResponse> getAll(int pageNumber, int pageSize);
    PageDto<GetUserResponse> getAllByRoleId(int pageNumber, int pageSize, int roleId);
    PageDto<GetUserResponse> getAllByRoleName(int pageNumber, int pageSize, String name);
    GetUserResponse changePassword(ChangeUserPasswordRequest request);
    GetUserResponse changeRole(int roleId);
    void delete(int id);
}
