package com.example.meongnyangbook.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

  @GetMapping("/mya/view/post/adoptions")
  public String adoptions() {
    return "post-adoptions";
  }

  @GetMapping("/mya/view/post/add")
  public String addPost() {
    return "post-add";
  }

  @GetMapping("/mya/view/post/adoption-add")
  public String addAdoptionPost() {
    return "post-adoptions-add";
  }

  @GetMapping("/mya/view/post/community/detail")
  public String detailCommunityPost() {
    return "post-community-details";
  }

  @GetMapping("/mya/view/basket")
  public String basket() {return "shopping-cart";}

  @GetMapping("/mya/view/items/{itemId}")
  public String itemDetail(@PathVariable Long itemId) {return "shop-details";}

  @GetMapping("/mya/view/order")
  public String checkOut() {return "order";}

  @GetMapping("/mya/view/communities/{communityId}")
  public String detailsCommunityPost(@PathVariable Long communityId) {
    return "post-community-details";
  }

  @GetMapping("/mya/view/adoptions/{adoptionId}")
  public String detailsAdoptionsPost(@PathVariable Long adoptionId) {
    return "post-adoptions-details";
  }

  @GetMapping("/mya/view/post/community/update/{communityId}")
  public String updateCommunityPost(@PathVariable Long communityId) {
    return
        "post-community-update";
  }

  @GetMapping("/mya/view/chat")
  public String chat() {return "chat";}

  @GetMapping("/mya/view/post/adoptions/update/{adoptionsId}")
  public String updateAdoptionsPost(@PathVariable Long adoptionsId) {
    return
        "post-adoptions-update";
  }

  @GetMapping("/mya/view/fcm")
  public String testFcm() {
    return "fcm";
  }
  @GetMapping("/mya/view/users/relative-profile/{userNo}")
  public String relativeProfile(@PathVariable Long userNo) {
    return "relativeProfile";
  }
}
