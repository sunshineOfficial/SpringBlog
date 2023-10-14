package ru.pnzgu.springblog.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pnzgu.springblog.dto.common.PageDto;
import ru.pnzgu.springblog.dto.role.ChangeRoleNameRequest;
import ru.pnzgu.springblog.dto.role.CreateRoleRequest;
import ru.pnzgu.springblog.dto.role.GetRoleResponse;
import ru.pnzgu.springblog.services.RoleService;

/**
 * Контроллер ролей пользователя.
 */
@RestController
@RequestMapping("/api/role")
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Создает новую роль. 
     *
     * @param request запрос на создание роли 
     * @return ответ с кодом состояния 200 (ОК) и идентификатором созданной роли 
     */
    @PostMapping
    public ResponseEntity<Integer> create(@Valid @RequestBody CreateRoleRequest request) {
        return ResponseEntity.ok(roleService.create(request));
    }

    /**
     * Получает роль по указанному идентификатору. 
     *
     * @param id идентификатор роли 
     * @return ответ с кодом состояния 200 (ОК), содержащим роль с указанным идентификатором 
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetRoleResponse> getById(@PathVariable int id) {
        return ResponseEntity.ok(roleService.getById(id));
    }

    /**
     * Возвращает список ролей с возможностью фильтрации по параметрам. 
     *
     * @param pageNumber номер страницы 
     * @param pageSize   количество ролей на странице
     * @return ответ с кодом состояния 200 (ОК), содержащим список ролей с учетом фильтрации 
     */
    @GetMapping
    public ResponseEntity<PageDto<GetRoleResponse>> getAll(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "10", required = false) int pageSize
    ) {
        return ResponseEntity.ok(roleService.getAll(pageNumber, pageSize));
    }

    /**
     * Обновляет роль по заданному идентификатору. 
     *
     * @param id      идентификатор роли 
     * @param request объект запроса на обновление роли
     * @return ответ с кодом состояния 200 (ОК), содержащим обновленную роль
     */
    @PutMapping("/{id}")
    public ResponseEntity<GetRoleResponse> changeName(@PathVariable int id, @Valid @RequestBody ChangeRoleNameRequest request) {
        return ResponseEntity.ok(roleService.changeName(id, request));
    }

    /**
     * Удаляет роль по указанному идентификатору. 
     *
     * @param id идентификатор роли 
     * @return ответ с сообщением об успешном удалении роли 
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        roleService.delete(id);
        return ResponseEntity.ok("Role deleted successfully");
    }
}
