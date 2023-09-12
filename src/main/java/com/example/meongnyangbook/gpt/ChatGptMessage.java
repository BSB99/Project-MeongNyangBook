package com.example.meongnyangbook.gpt;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatGptMessage implements Serializable {

  private String role;
  private String content;

  @Builder
  public ChatGptMessage(String role, String content) {
    this.role = role;
    this.content = content;
  }
}