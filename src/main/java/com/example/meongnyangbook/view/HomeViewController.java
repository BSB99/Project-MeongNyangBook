package com.example.meongnyangbook.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeViewController {

  @GetMapping
  public String home() {
    return "index";
  }

  @GetMapping("/mya/view/users/sign-in")
  public String signIn() {
    return "login";
  }

}
