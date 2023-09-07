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

