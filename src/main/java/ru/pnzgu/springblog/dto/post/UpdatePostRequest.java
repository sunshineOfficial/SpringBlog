package ru.pnzgu.springblog.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdatePostRequest {
    @Size(min = 3, max = 100)
    private String title;

    @NotBlank
    private String content;
}
