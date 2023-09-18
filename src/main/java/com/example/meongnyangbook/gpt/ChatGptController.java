package com.example.meongnyangbook.gpt;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "GPT API")
@RequiredArgsConstructor
@RequestMapping("/chat-gpt")
@RestController
public class ChatGptController {

  //  private final APIResponse apiResponse;
  private final ChatGptService chatGptService;

  @Operation(summary = "Chat-GPT에게 질문하기")
  @PostMapping("/question")
  public String sendQuestion(
      @RequestBody QuestionRequest questionRequest) {

    ChatGptResponse chatGptResponse = null;
    try {
      chatGptResponse = chatGptService.askQuestion(questionRequest);
    } catch (Exception e) {

    }
    //return 부분은 자유롭게 수정하시면됩니다. ex)return chatGptResponse;
    return chatGptResponse.getChoices().get(0).getMessage().getContent();
  }
}