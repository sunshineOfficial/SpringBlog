package ru.pnzgu.springblog.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pnzgu.springblog.dto.common.PageDto;
import ru.pnzgu.springblog.dto.user.ChangeRoleRequest;
import ru.pnzgu.springblog.dto.user.ChangeUserPasswordRequest;
import ru.pnzgu.springblog.dto.user.GetUserResponse;
import ru.pnzgu.springblog.services.UserService;

import java.util.Optional;

/**
 * Контроллер пользователей.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Получает пользователя, отправившего запрос. 
     *
     * @return ответ с кодом состояния 200 (ОК), содержащим пользователя
     */
    @GetMapping("/current")
    public ResponseEntity<GetUserResponse> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    /**
     * Получает пользователя по указанному идентификатору. 
     *
     * @param id идентификатор пользователя 
     * @return ответ с кодом состояния 200 (ОК), содержащим пользователя с указанным идентификатором 
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetUserResponse> getById(@PathVariable int id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    /**
     * Возвращает список пользователей с возможностью фильтрации по параметрам. 
     *
     * @param pageNumber номер страницы 
     * @param pageSize   количество пользователей на странице 
     * @param roleId     идентификатор роли, которую должен иметь пользователь
     * @param roleName   название роли, которую должен иметь пользователь
     * @return ответ с кодом состояния 200 (ОК), содержащим список пользователей с учетом фильтрации 
     */
    @GetMapping
    public ResponseEntity<PageDto<GetUserResponse>> getAll(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "10", required = false) int pageSize,
            @RequestParam Optional<Integer> roleId,
            @RequestParam Optional<String> roleName
    ) {
        if (roleId.isPresent()) return ResponseEntity.ok(userService.getAllByRoleId(pageNumber, pageSize, roleId.get()));
        if (roleName.isPresent()) return ResponseEntity.ok(userService.getAllByRoleName(pageNumber, pageSize, roleName.get()));
        
        return ResponseEntity.ok(userService.getAll(pageNumber, pageSize));
    }

    /**
     * Меняет пароль пользователя. 
     *
     * @param request объект запроса на смену пароля
     * @return ответ с кодом состояния 200 (ОК), содержащим пользователя
     */
    @PutMapping("/password")
    public ResponseEntity<GetUserResponse> changePassword(@Valid @RequestBody ChangeUserPasswordRequest request) {
        return ResponseEntity.ok(userService.changePassword(request));
    }

    /**
     * Меняет роль пользователя. 
     *
     * @param id      идентификатор пользователя
     * @param request объект запроса на смену роли
     * @return ответ с кодом состояния 200 (ОК), содержащим обновленного пользователя
     */
    @PutMapping("/{id}/role")
    public ResponseEntity<GetUserResponse> changeRole(@PathVariable int id, @Valid @RequestBody ChangeRoleRequest request) {
        return ResponseEntity.ok(userService.changeRole(id, request));
    }

    /**
     * Удаляет пользователя по указанному идентификатору. 
     *
     * @param id идентификатор пользователя 
     * @return ответ с сообщением об успешном удалении пользователя 
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        userService.delete(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
