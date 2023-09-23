package ru.pnzgu.springblog.dto.comment;

import lombok.Data;

@Data
public class CreateCommentRequest {
    private int userId;
    private int postId;
    private String content;
}
