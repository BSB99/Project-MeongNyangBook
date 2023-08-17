package com.example.meongnyangbook.post.adoption.entity;

import com.example.meongnyangbook.post.dto.AdoptionReqeustDto;
import com.example.meongnyangbook.post.entity.AnimalGenderEnum;
import com.example.meongnyangbook.post.entity.AreaEnum;
import com.example.meongnyangbook.post.entity.CategoryEnum;
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
    @Enumerated(value = EnumType.STRING)
    private AnimalGenderEnum animalGender;

    @Column(name = "animal_age", nullable = false)
    private Integer animalAge;

    @Column(name = "area", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AreaEnum area;

    @Column(name = "category", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CategoryEnum category;

    public Adoption(AdoptionReqeustDto requestDto, User user) {
        this.title = requestDto.getTitle();
        this.description = requestDto.getDescription();
        this.completion = requestDto.getCompletion();
        this.animalName = requestDto.getAnimalName();
        this.animalGender = requestDto.getAnimalGender();
        this.animalAge = requestDto.getAnimalAge();
        this.area = requestDto.getArea();
        this.category = requestDto.getCategory();
        this.user = user;
    }
}
