package ru.pnzgu.springblog.services;

import ru.pnzgu.springblog.dto.common.PageDto;
import ru.pnzgu.springblog.dto.role.GetRoleResponse;

public interface RoleService {
    int create(String name);
    GetRoleResponse getById(int id);
    PageDto<GetRoleResponse> getAll(int pageNumber, int pageSize);
    GetRoleResponse changeName(String name);
    void delete(int id);
}
