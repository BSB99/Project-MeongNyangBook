package com.example.meongnyangbook.gpt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GPTService {

  private final RestTemplate restTemplate;
  private final String gptApiUrl;
  private final String apiKey;

  public GPTService(RestTemplate restTemplate, @Value("${gpt.api.url}") String gptApiUrl,
      @Value("${gpt.api-key}") String apiKey) {
    this.restTemplate = restTemplate;
    this.gptApiUrl = gptApiUrl;
    this.apiKey = apiKey;
  }

  public String generateText(String inputText) {
    String endpointUrl = gptApiUrl + "/completions"; // GPT-3 API의 엔드포인트 URL
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + apiKey);
    headers.set("Content-Type", "application/json;charset=UTF-8");

    List<Map<String, Object>> messages = new ArrayList<>();
    Map<String, Object> message = new HashMap<>();
    message.put("role", "system");
    message.put("content", "You are a helpful assistant.");
    messages.add(message);

    message = new HashMap<>();
    message.put("role", "user");
    message.put("content", inputText);
    messages.add(message);

    Map<String, Object> requestBody = new HashMap<>();
    requestBody.put("messages", messages);
    requestBody.put("model", "gpt-3.5-turbo");
    requestBody.put("max_tokens", 500);
    requestBody.put("temperature", 0.7);

    HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

    ResponseEntity<String> response = restTemplate.exchange(
        endpointUrl,
        HttpMethod.POST,
        requestEntity,
        String.class
    );

    if (response.getStatusCode() == HttpStatus.OK) {
      return response.getBody();
    } else {
      // 오류 처리
      throw new RuntimeException("GPT API 호출 중 오류 발생: " + response.getStatusCode());
    }
  }
}
