package ru.pnzgu.springblog.dto.post;

import lombok.Data;

@Data
public class UpdatePostRequest {
    private String title;
    private String content;
}
