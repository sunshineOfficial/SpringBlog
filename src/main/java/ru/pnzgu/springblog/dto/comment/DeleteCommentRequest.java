package ru.pnzgu.springblog.dto.comment;

import lombok.Data;

@Data
public class DeleteCommentRequest {
    private int userId;
    private int postId;
}
