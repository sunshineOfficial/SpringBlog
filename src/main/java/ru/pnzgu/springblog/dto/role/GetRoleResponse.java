package ru.pnzgu.springblog.dto.role;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class GetRoleResponse {
    private int id;
    private String name;
    private Date createdAt;
    private Date updatedAt;
}
