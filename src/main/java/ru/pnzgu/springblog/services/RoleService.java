package ru.pnzgu.springblog.services;

import ru.pnzgu.springblog.dto.common.PageDto;
import ru.pnzgu.springblog.dto.role.CreateRoleRequest;
import ru.pnzgu.springblog.dto.role.GetRoleResponse;
import ru.pnzgu.springblog.dto.role.ChangeRoleNameRequest;

/**
 * Интерфейс сервиса ролей пользователя.
 */
public interface RoleService {
    /**
     * Создает новую роль. 
     *
     * @param request запрос на создание роли 
     * @return идентификатор созданной роли 
     */
    int create(CreateRoleRequest request);

    /**
     * Получает роль по указанному идентификатору. 
     *
     * @param id идентификатор роли 
     * @return роль с указанным идентификатором 
     */
    GetRoleResponse getById(int id);

    /**
     * Получает все роли.
     *
     * @param pageNumber номер страницы
     * @param pageSize   количество ролей на странице
     * @return объект PageDto, содержащий роли
     */
    PageDto<GetRoleResponse> getAll(int pageNumber, int pageSize);

    /**
     * Обновляет роль по заданному идентификатору. 
     *
     * @param id      идентификатор роли 
     * @param request объект запроса на обновление роли
     * @return обновленная роль
     */
    GetRoleResponse changeName(int id, ChangeRoleNameRequest request);

    /**
     * Удаляет роль по указанному идентификатору. 
     *
     * @param id идентификатор роли
     */
    void delete(int id);
}
