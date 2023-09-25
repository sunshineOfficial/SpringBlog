package ru.pnzgu.springblog.dto.comment;

import lombok.Data;

@Data
public class ChangeCommentContentRequest {
    private String content;
}
