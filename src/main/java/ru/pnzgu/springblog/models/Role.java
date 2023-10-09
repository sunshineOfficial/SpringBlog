package ru.pnzgu.springblog.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.*;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @OneToMany(mappedBy = "role")
    private List<UserEntity> users = new ArrayList<>();

    @Column(unique = true, nullable = false)
    private String name;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    public Role(String name) {
        this.name = name;
    }
}
