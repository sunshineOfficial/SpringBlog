package ru.pnzgu.springblog.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangeCommentContentRequest {
    @NotBlank
    private String content;
}
