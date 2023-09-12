package com.example.meongnyangbook.gpt;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class GPTService {

  private final WebClient webClient;
  private final String gptApiUrl;
  private final String apiKey;
  private final String gptModelId;

  public GPTService(@Value("${gpt.api.url}") String gptApiUrl,
      @Value("${gpt.api-key}") String apiKey,
      @Value("${gpt.api-modelId}") String gptModelId) {
    this.webClient = WebClient.builder()
        .baseUrl(gptApiUrl)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
        .build();
    this.gptApiUrl = gptApiUrl;
    this.apiKey = apiKey;
    this.gptModelId = gptModelId;
  }

  public String generateText(String inputText) {
    return webClient.post()
        .uri("/completions")
        .body(BodyInserters.fromValue(Map.of(
            "model", gptModelId,
            "prompt", inputText,
            "max_tokens", 50,
            "temperature", 0.5
        )))
        .retrieve()
        .bodyToMono(String.class)
        .block(); // 이 부분은 비동기 코드를 동기적으로 실행하기 위한 예제입니다. 실제로는 비동기 방식을 사용해야 합니다.
  }
}
