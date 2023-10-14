package ru.pnzgu.springblog.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

/**
 * Класс, представляющий сущность Комментарий.
 */
@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
public class Comment {
    /**
     * Идентификатор комментария.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Пользователь, создавший комментарий.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    /**
     * Пост, к которому относится комментарий.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    /**
     * Содержимое комментария.
     */
    @Lob
    @Column(nullable = false)
    private String content;

    /**
     * Флаг, указывающий, опубликован ли комментарий.
     */
    @Column(nullable = false)
    private boolean published = false;

    /**
     * Дата и время создания комментария.
     */
    @CreationTimestamp
    private Date createdAt;

    /**
     * Дата и время последнего обновления комментария.
     */
    @UpdateTimestamp
    private Date updatedAt;

    /**
     * Дата и время публикации комментария.
     */
    private Date publishedAt;

    /**
     * Конструктор класса Comment.
     * 
     * @param user    пользователь, создавший комментарий
     * @param post    пост, к которому относится комментарий
     * @param content содержимое комментария
     */
    public Comment(UserEntity user, Post post, String content) {
        this.user = user;
        this.post = post;
        this.content = content;
    }
}
