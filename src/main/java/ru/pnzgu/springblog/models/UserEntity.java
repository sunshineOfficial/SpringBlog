package ru.pnzgu.springblog.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.*;

/**
 * Класс, представляющий сущность Пользователь.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class UserEntity {
    /**
     * Идентификатор пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Роль пользователя.
     */
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    /**
     * Посты пользователя.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    /**
     * Комментарии пользователя.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    /**
     * Логин пользователя.
     */
    @Column(unique = true, nullable = false)
    private String username;

    /**
     * Пароль пользователя.
     */
    @Column(nullable = false)
    private String password;

    /**
     * Имя пользователя.
     */
    @Column(nullable = false)
    private String firstName;

    /**
     * Фамилия пользователя.
     */
    @Column(nullable = false)
    private String lastName;

    /**
     * Дата и время создания пользователя.
     */
    @CreationTimestamp
    private Date createdAt;

    /**
     * Дата и время последнего обновления роли.
     */
    @UpdateTimestamp
    private Date updatedAt;

    /**
     * Конструктор класса UserEntity.
     * 
     * @param role      роль пользователя
     * @param username  логин пользователя
     * @param password  пароль пользователя
     * @param firstName имя пользователя
     * @param lastName  фамилия пользователя
     */
    public UserEntity(Role role, String username, String password, String firstName, String lastName) {
        this.role = role;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
