package com.example.meongnyangbook.alarm.fcm;

import com.example.meongnyangbook.alarm.AlarmCategoryEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FcmServiceDto {

  private String username;
  private Long contentId;
  private AlarmCategoryEnum type;
  private String title;
  private String content;

  public static FcmServiceDto of(String username, Long contentId, AlarmCategoryEnum type,
      String title,
      String content) {
    FcmServiceDto dto = new FcmServiceDto();
    dto.username = username;
    dto.contentId = contentId;
    dto.type = type;
    dto.title = title;
    dto.content = content;
    return dto;
  }
}
