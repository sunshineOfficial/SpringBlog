package ru.pnzgu.springblog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pnzgu.springblog.dto.common.PageDto;
import ru.pnzgu.springblog.dto.user.ChangeRoleRequest;
import ru.pnzgu.springblog.dto.user.ChangeUserPasswordRequest;
import ru.pnzgu.springblog.dto.user.GetUserResponse;
import ru.pnzgu.springblog.services.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<GetUserResponse> getById(@PathVariable int id) {
        return ResponseEntity.ok(userService.getById(id));
    }
    
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
    
    @PutMapping("/password")
    public ResponseEntity<GetUserResponse> changePassword(@RequestBody ChangeUserPasswordRequest request) {
        return ResponseEntity.ok(userService.changePassword(request));
    }
    
    @PutMapping("/role")
    public ResponseEntity<GetUserResponse> changeRole(@RequestBody ChangeRoleRequest request) {
        return ResponseEntity.ok(userService.changeRole(request));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        userService.delete(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
