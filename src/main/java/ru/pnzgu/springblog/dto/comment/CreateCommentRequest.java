package ru.pnzgu.springblog.dto.comment;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCommentRequest {
    @Min(1)
    private int postId;
    
    @NotBlank
    private String content;
}
