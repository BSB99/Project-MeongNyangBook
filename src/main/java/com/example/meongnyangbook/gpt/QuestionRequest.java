package com.example.meongnyangbook.gpt;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
//Front단에서 요청하는 DTO
public class QuestionRequest implements Serializable {

  private String question;
}