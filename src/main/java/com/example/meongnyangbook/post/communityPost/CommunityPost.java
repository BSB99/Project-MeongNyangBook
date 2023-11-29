package com.example.meongnyangbook.post.communityPost;

import com.example.meongnyangbook.post.dto.PostRequestDto;
import com.example.meongnyangbook.post.entity.Post;
import com.example.meongnyangbook.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor
@DynamicUpdate
@Table(name = "community_posts")
public class CommunityPost extends Post {

  //커뮤니티
  public CommunityPost(PostRequestDto requestDto, User user) {
    this.title = requestDto.getTitle();
    this.description = requestDto.getDescription();
    this.user = user;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
