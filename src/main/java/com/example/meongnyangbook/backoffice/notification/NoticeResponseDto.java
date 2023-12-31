package com.example.meongnyangbook.backoffice.notification;

import lombok.Getter;

@Getter
public class NoticeResponseDto {

  public Long id;
  public String title;
  public String description;
  public String createdAt;

  public NoticeResponseDto(Notice notice) {
    this.id = notice.getId();
    this.title = notice.getTitle();
    this.description = notice.getDescription();
    this.createdAt = notice.getCreatedAtAsString();
  }
}
