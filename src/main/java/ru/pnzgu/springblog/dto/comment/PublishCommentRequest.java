package ru.pnzgu.springblog.dto.comment;

import lombok.Data;

@Data
public class PublishCommentRequest {
    private int userId;
    private int postId;
}
