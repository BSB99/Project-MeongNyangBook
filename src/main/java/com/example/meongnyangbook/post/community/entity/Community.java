package com.example.meongnyangbook.post.community.entity;

import com.example.meongnyangbook.post.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "community_posts")
public class Community extends Post {
}
