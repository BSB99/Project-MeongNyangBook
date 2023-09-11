//package com.example.meongnyangbook.gpt;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/gpt")
//public class GPTController {
//
//  private final GPTService gptService;
//
//  public GPTController(GPTService gptService) {
//    this.gptService = gptService;
//  }
//
//  @PostMapping("/generate")
//  public ResponseEntity<String> generateText(@RequestBody String inputText) {
//    String generatedText = gptService.generateText(inputText);
//    return ResponseEntity.ok(generatedText);
//  }
//}