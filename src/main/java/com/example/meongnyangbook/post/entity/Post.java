package com.example.meongnyangbook.post.entity;

import com.example.meongnyangbook.entity.TimeStamped;
import com.example.meongnyangbook.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@MappedSuperclass
@Entity
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Post extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(name = "title", nullable = false)
    protected String title;

    @Column(name = "description", nullable = false)
    protected String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    protected User user;
}
