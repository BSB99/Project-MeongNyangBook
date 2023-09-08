package com.example.meongnyangbook.view;

import com.example.meongnyangbook.user.UserDetailsImpl;
import com.example.meongnyangbook.user.UserRoleEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mya/back-office")
@Slf4j
public class BackOfficeViewController {

  @GetMapping
  public String backOffice() {
    return "back-office/index";
  }

  @GetMapping("/admin")
  @Secured(UserRoleEnum.Authority.ADMIN) // 관리자용
  public String backofficeSecurity(@AuthenticationPrincipal UserDetailsImpl userDetails) {
    System.out.println(userDetails.getAuthorities());
    return "back-office/index";
  }

  @GetMapping("/error")
  public String backofficeError() {
    return "/back-office/pages/404-page";
  }

  @GetMapping("/notice")
  public String backOfficeNotice() {
    return "back-office/pages/notice-table";
  }

  @GetMapping("/report")
  public String backOfficeReport() {
    return "back-office/pages/report-table";
  }

  @GetMapping("/item-list")
  public String backOfficeItemList() {
    return "back-office/ecommerce-product-list";
  }

  @GetMapping("/item-save")
  public String backOfficeItemSave() {
    return "back-office/ecommerce-product-save";
  }

  @GetMapping("/order")
  public String backOfficeOrder() {
    return "back-office/pages/order-table";
  }
}

