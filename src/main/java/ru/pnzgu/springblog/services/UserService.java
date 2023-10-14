package ru.pnzgu.springblog.services;

import ru.pnzgu.springblog.dto.common.PageDto;
import ru.pnzgu.springblog.dto.user.ChangeRoleRequest;
import ru.pnzgu.springblog.dto.user.ChangeUserPasswordRequest;
import ru.pnzgu.springblog.dto.user.GetUserResponse;

/**
 * Интерфейс сервиса пользователей.
 */
public interface UserService {
    /**
     * Получает пользователя, отправившего запрос. 
     *
     * @return пользователя
     */
    GetUserResponse getCurrentUser();

    /**
     * Получает пользователя по указанному идентификатору. 
     *
     * @param id идентификатор пользователя 
     * @return пользователя с указанным идентификатором 
     */
    GetUserResponse getById(int id);

    /**
     * Получает всех пользователей.
     *
     * @param pageNumber номер страницы
     * @param pageSize   количество пользователей на странице
     * @return объект PageDto, содержащий пользователей
     */
    PageDto<GetUserResponse> getAll(int pageNumber, int pageSize);

    /**
     * Получает всех пользователей, отфильтрованных по идентификатору роли.
     *
     * @param pageNumber номер страницы
     * @param pageSize   количество пользователей на странице
     * @param roleId     идентификатор роли, которую должен иметь пользователь
     * @return объект PageDto, содержащий пользователей
     */
    PageDto<GetUserResponse> getAllByRoleId(int pageNumber, int pageSize, int roleId);

    /**
     * Получает всех пользователей, отфильтрованных по названию роли.
     *
     * @param pageNumber номер страницы
     * @param pageSize   количество пользователей на странице
     * @param name       название роли, которую должен иметь пользователь
     * @return объект PageDto, содержащий пользователей
     */
    PageDto<GetUserResponse> getAllByRoleName(int pageNumber, int pageSize, String name);

    /**
     * Меняет пароль пользователя. 
     *
     * @param request объект запроса на смену пароля
     * @return пользователя
     */
    GetUserResponse changePassword(ChangeUserPasswordRequest request);

    /**
     * Меняет роль пользователя. 
     *
     * @param id      идентификатор пользователя
     * @param request объект запроса на смену роли
     * @return обновленного пользователя
     */
    GetUserResponse changeRole(int id, ChangeRoleRequest request);

    /**
     * Удаляет пользователя по указанному идентификатору. 
     *
     * @param id идентификатор пользователя
     */
    void delete(int id);
}
