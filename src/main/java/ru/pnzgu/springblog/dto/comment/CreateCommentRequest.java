package ru.pnzgu.springblog.dto.comment;

import lombok.Data;

@Data
public class CreateCommentRequest {
    private int postId;
    private String content;
}
