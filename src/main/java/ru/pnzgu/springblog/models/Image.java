package ru.pnzgu.springblog.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс, представляющий сущность Изображение.
 */
@Entity
@Table(name = "images")
@Data
@NoArgsConstructor
public class Image {
    /**
     * Идентификатор изображения.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Пользователь с аватаром.
     */
    @OneToOne(mappedBy = "avatar")
    private UserEntity user;

    /**
     * Пост с изображением.
     */
    @OneToOne(mappedBy = "image")
    private Post post;

    /**
     * Название изображения.
     */
    @Column(unique = true, nullable = false)
    private String name;

    /**
     * Тип изображения.
     */
    @Column(unique = true, nullable = false)
    private String type;

    /**
     * Данные изображения.
     */
    @Lob
    @Column(length = 1000)
    private byte[] data;

    /**
     * Конструктор класса Image.
     * 
     * @param name название изображения
     * @param type тип изображения
     * @param data данные изображения
     */
    public Image(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }
}
