package ru.pnzgu.springblog.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class GetPostResponse {
    private int id;
    private int userId;
    private String title;
    private String content;
    private boolean published;
    private Date createdAt;
    private Date updatedAt;
    private Date publishedAt;
}
