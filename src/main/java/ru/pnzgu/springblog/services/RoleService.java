package ru.pnzgu.springblog.services;

import ru.pnzgu.springblog.dto.common.PageDto;
import ru.pnzgu.springblog.dto.role.CreateRoleRequest;
import ru.pnzgu.springblog.dto.role.GetRoleResponse;
import ru.pnzgu.springblog.dto.role.ChangeRoleNameRequest;

public interface RoleService {
    int create(CreateRoleRequest request);
    GetRoleResponse getById(int id);
    PageDto<GetRoleResponse> getAll(int pageNumber, int pageSize);
    GetRoleResponse changeName(int id, ChangeRoleNameRequest request);
    void delete(int id);
}
