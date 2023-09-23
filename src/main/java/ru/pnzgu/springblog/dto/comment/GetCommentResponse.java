package ru.pnzgu.springblog.dto.comment;

import lombok.Data;

import java.util.Date;

@Data
public class GetCommentResponse {
    private int userId;
    private int postId;
    private String content;
    private boolean published;
    private Date createdAt;
    private Date updatedAt;
    private Date publishedAt;
}
