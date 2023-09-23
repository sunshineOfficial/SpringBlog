package ru.pnzgu.springblog.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentKey implements Serializable {
    @Column(name = "user_id")
    private int userId;

    @Column(name = "post_id")
    private int postId;
}
