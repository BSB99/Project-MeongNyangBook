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

  @GetMapping("/mya/view/users/my-profile")
  public String myProfile() {
    return "profile";
  }

  @GetMapping("/mya/view/items")
  public String shop() {
    return "shop";
  }

  @GetMapping("/mya/view/post/community")
  public String community() {
    return "post-community";
  }

  @GetMapping("/mya/view/post/add")
  public String addPost() {
    return "post-add";
  }

  @GetMapping("/mya/view/post/community/detail")
  public String detailCommunityPost() {
    return "post-community-details";
  }

  @GetMapping("/mya/view/basket")
  public String basket() {return "shopping-cart";}

  @GetMapping("/mya/view/basket-detail")
  public String basketDetail() {return "shop-details";}

  @GetMapping("/mya/view/order")
  public String checkOut() {return "checkout";}
}
