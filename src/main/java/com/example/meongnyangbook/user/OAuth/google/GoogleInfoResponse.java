package com.example.meongnyangbook.user.OAuth.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true) //필요없는 속성 무시
@AllArgsConstructor
@NoArgsConstructor
public class GoogleInfoResponse {

  @JsonProperty("sub")
  private String username;

  @JsonProperty("name")
  private String nickname;
}