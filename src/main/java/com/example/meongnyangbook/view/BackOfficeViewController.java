package com.example.meongnyangbook.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mya/back-office")
public class BackOfficeViewController {

  @GetMapping
  public String backOffice() {
    return "back-office/index";
  }
}
