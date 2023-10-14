package ru.pnzgu.springblog.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.*;

/**
 * Класс, представляющий сущность Роль.
 */
@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
public class Role {
    /**
     * Идентификатор роли.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Пользователи, имеющие данную роль.
     */
    @OneToMany(mappedBy = "role")
    private List<UserEntity> users = new ArrayList<>();

    /**
     * Название роли.
     */
    @Column(unique = true, nullable = false)
    private String name;

    /**
     * Дата и время создания роли.
     */
    @CreationTimestamp
    private Date createdAt;

    /**
     * Дата и время последнего обновления роли.
     */
    @UpdateTimestamp
    private Date updatedAt;

    /**
     * Конструктор класса Role.
     * 
     * @param name название роли
     */
    public Role(String name) {
        this.name = name;
    }
}
