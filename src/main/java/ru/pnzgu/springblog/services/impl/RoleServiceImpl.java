package ru.pnzgu.springblog.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.pnzgu.springblog.dto.common.PageDto;
import ru.pnzgu.springblog.dto.role.ChangeRoleNameRequest;
import ru.pnzgu.springblog.dto.role.CreateRoleRequest;
import ru.pnzgu.springblog.dto.role.GetRoleResponse;
import ru.pnzgu.springblog.exceptions.EntityAlreadyExistsException;
import ru.pnzgu.springblog.exceptions.EntityNotFoundException;
import ru.pnzgu.springblog.helpers.PageDtoMaker;
import ru.pnzgu.springblog.models.Role;
import ru.pnzgu.springblog.repositories.RoleRepository;
import ru.pnzgu.springblog.services.RoleService;

import java.util.Objects;

/**
 * Класс сервиса ролей пользователя.
 */
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PageDtoMaker<Role, GetRoleResponse> pageDtoMaker;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, PageDtoMaker<Role, GetRoleResponse> pageDtoMaker) {
        this.roleRepository = roleRepository;
        this.pageDtoMaker = pageDtoMaker;
    }

    /**
     * Создает новую роль. 
     *
     * @param request запрос на создание роли 
     * @return идентификатор созданной роли 
     */
    @Override
    public int create(CreateRoleRequest request) {
        if (roleRepository.existsByName(request.getName()))
            throw new EntityAlreadyExistsException("Role with such name already exists");
        
        var newRole = roleRepository.save(new Role(request.getName()));
        
        return newRole.getId();
    }

    /**
     * Получает роль по указанному идентификатору. 
     *
     * @param id идентификатор роли 
     * @return роль с указанным идентификатором 
     */
    @Override
    public GetRoleResponse getById(int id) {
        var role = roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Role not found"));
        
        return mapToResponse(role);
    }

    /**
     * Получает все роли.
     *
     * @param pageNumber номер страницы
     * @param pageSize   количество ролей на странице
     * @return объект PageDto, содержащий роли
     */
    @Override
    public PageDto<GetRoleResponse> getAll(int pageNumber, int pageSize) {
        var pageRequest = PageRequest.of(pageNumber, pageSize);
        var page = roleRepository.findAll(pageRequest);

        return pageDtoMaker.makePageDto(page, this::mapToResponse);
    }

    /**
     * Обновляет роль по заданному идентификатору. 
     *
     * @param id      идентификатор роли 
     * @param request объект запроса на обновление роли
     * @return обновленная роль
     */
    @Override
    public GetRoleResponse changeName(int id, ChangeRoleNameRequest request) {
        var role = roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Role not found"));

        if (roleRepository.existsByName(request.getName()) && !Objects.equals(role.getName(), request.getName()))
            throw new EntityAlreadyExistsException("Role with such name already exists");
        
        role.setName(request.getName());
        
        var updatedRole = roleRepository.save(role);
        
        return mapToResponse(updatedRole);
    }

    /**
     * Удаляет роль по указанному идентификатору. 
     *
     * @param id идентификатор роли
     */
    @Override
    public void delete(int id) {
        var role = roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Role not found"));
        roleRepository.delete(role);
    }

    /**
     * Преобразует Role в GetRoleResponse.
     *
     * @param role объект Role, который будет преобразован
     * @return объект GetRoleResponse, содержащий данные из объекта Role
     */
    private GetRoleResponse mapToResponse(Role role) {
        return new GetRoleResponse(role.getId(), role.getName(), role.getCreatedAt(), role.getUpdatedAt());
    }
}
