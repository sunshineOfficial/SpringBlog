package ru.pnzgu.springblog.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.*;

/**
 * Класс, представляющий сущность Пост.
 */
@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
public class Post {
    /**
     * Идентификатор поста.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Пользователь, создавший пост.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    /**
     * Комментарии, относящиеся к посту.
     */
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    /**
     * Заголовок поста.
     */
    @Column(nullable = false)
    private String title;

    /**
     * Содержимое поста.
     */
    @Lob
    @Column(nullable = false)
    private String content;

    /**
     * Флаг, указывающий, опубликован ли пост.
     */
    @Column(nullable = false)
    private boolean published = false;

    /**
     * Дата и время создания поста.
     */
    @CreationTimestamp
    private Date createdAt;

    /**
     * Дата и время последнего обновления поста.
     */
    @UpdateTimestamp
    private Date updatedAt;

    /**
     * Дата и время публикации поста.
     */
    private Date publishedAt;

    /**
     * Конструктор класса Post.
     * 
     * @param user    пользователь, создавший пост
     * @param title   заголовок поста
     * @param content содержимое поста
     */
    public Post(UserEntity user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
    }
}
