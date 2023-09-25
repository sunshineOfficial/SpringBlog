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

@RestController
@RequestMapping("/api/role")
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }
    
    @PostMapping
    public ResponseEntity<Integer> create(@Valid @RequestBody CreateRoleRequest request) {
        return ResponseEntity.ok(roleService.create(request));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<GetRoleResponse> getById(@PathVariable int id) {
        return ResponseEntity.ok(roleService.getById(id));
    }
    
    @GetMapping
    public ResponseEntity<PageDto<GetRoleResponse>> getAll(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "10", required = false) int pageSize
    ) {
        return ResponseEntity.ok(roleService.getAll(pageNumber, pageSize));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<GetRoleResponse> changeName(@PathVariable int id, @Valid @RequestBody ChangeRoleNameRequest request) {
        return ResponseEntity.ok(roleService.changeName(id, request));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        roleService.delete(id);
        return ResponseEntity.ok("Role deleted successfully");
    }
}
