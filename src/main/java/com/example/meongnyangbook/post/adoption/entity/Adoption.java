package com.example.meongnyangbook.post.adoption.entity;

import com.example.meongnyangbook.entity.Area;
import com.example.meongnyangbook.entity.Category;
import com.example.meongnyangbook.post.entity.Post;
import com.example.meongnyangbook.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "adoption_posts")
public class Adoption extends Post {

    @Column(name = "completion", nullable = false)
    private Boolean completion;

    @Column(name = "animal_name", nullable = false)
    private String animalName;

    @Column(name = "animal_gender", nullable = false)
    private String animalGender;

    @Column(name = "animal_age", nullable = false)
    private Integer animalAge;

    @OneToOne
    @JoinColumn(name = "area_no")
    private Area area;

    @OneToOne
    @JoinColumn(name = "category_no")
    private Category category;
}
