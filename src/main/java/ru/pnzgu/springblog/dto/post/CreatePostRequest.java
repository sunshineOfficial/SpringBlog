package ru.pnzgu.springblog.dto.post;

import lombok.Data;

@Data
public class CreatePostRequest {
    private String title;
    private String content;
}
