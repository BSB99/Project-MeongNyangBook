package com.example.meongnyangbook.aop;

import com.example.meongnyangbook.user.UserDetailsImpl;
import com.example.meongnyangbook.user.UserRoleEnum;
import java.util.concurrent.RejectedExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j(topic = "AdminRoleCheckAop")
@Component
@Aspect
public class AdminRoleCheckAop {

  @Pointcut("execution(* com.example.meongnyangbook.report.ReportController.deleteReport(..))")
  private void deleteReport() {
  }

  @Pointcut("execution(* com.example.meongnyangbook.report.ReportController.getReports(..))")
  private void getReports() {
  }

  @Pointcut("execution(* com.example.meongnyangbook.backoffice.notification.NoticeController.createNotice(..))")
  private void createNotice() {
  }


  @Pointcut("execution(* com.example.meongnyangbook.backoffice.notification.NoticeController.getNotices(..))")
  private void getNotices() {
  }


  @Pointcut("execution(* com.example.meongnyangbook.backoffice.notification.NoticeController.updateNotice(..))")
  private void updateNotice() {
  }


  @Pointcut("execution(* com.example.meongnyangbook.backoffice.notification.NoticeController.deleteNotice(..))")
  private void deleteNotice() {
  }


  @Pointcut("execution(* com.example.meongnyangbook.shop.item.ItemController.deleteItem(..))")
  private void deleteItem() {
  }


  @Pointcut("execution(* com.example.meongnyangbook.shop.item.ItemController.createItem(..))")
  private void createItem() {
  }


  @Pointcut("execution(* com.example.meongnyangbook.shop.item.ItemController.updateItem(..))")
  private void updateItem() {
  }


  @Pointcut("execution(* com.example.meongnyangbook.shop.inquiry.inquiryComment.InquiryCommentController.createInquiryComment(..))")
  private void createInquiryComment() {
  }

  @Pointcut("execution(* com.example.meongnyangbook.shop.inquiry.inquiryComment.InquiryCommentController.updateInquiryComment(..))")
  private void updateInquiryComment() {
  }

  @Pointcut("execution(* com.example.meongnyangbook.shop.inquiry.inquiryComment.InquiryCommentController.deleteInquiryComment(..))")
  private void deleteInquiryComment() {
  }

  @Pointcut("execution(* com.example.meongnyangbook.view.BackOfficeViewController.backOffice())")
  private void backOffice() {

  }

  @Pointcut("execution(* com.example.meongnyangbook.view.BackOfficeViewController.backOfficeItemList())")
  private void backOfficeItemList() {

  }

  @Pointcut("execution(* com.example.meongnyangbook.view.BackOfficeViewController.backOfficeItemSave())")
  private void backOfficeItemSave() {
  }
  @Pointcut("execution(* com.example.meongnyangbook.shop.order.OrderController.getOrderLists(..))")
  private void findOrderLists() {
  }

  }

  @Pointcut("execution(* com.example.meongnyangbook.view.BackOfficeViewController.backOfficeNotice())")
  private void backOfficeNotice() {

  }

  @Pointcut("execution(* com.example.meongnyangbook.view.BackOfficeViewController.backOfficeReport())")
  private void backOfficeReport() {

  }

  @Pointcut("execution(* com.example.meongnyangbook.view.BackOfficeViewController.backOfficeOrder())")
  private void backOfficeOrder() {

  }

  @Around("deleteReport() || getReports() || createNotice() || getNotices() || updateNotice() ||"
      + " deleteNotice() || createItem() || updateItem() || deleteItem() || createInquiryComment() ||"
      + " updateInquiryComment() || deleteInquiryComment()||backOffice()||backOfficeItemList()||backOfficeItemSave()||"
      + "backOfficeNotice()||backOfficeReport()||backOfficeOrder()||findOrderLists()")
  public Object executeReportRoleCheck(ProceedingJoinPoint joinPoint) throws Throwable {

    UserDetailsImpl user = (UserDetailsImpl) joinPoint.getArgs()[0];
    if (user.getRole().equals(UserRoleEnum.BLOCK)) {
      throw new RejectedExecutionException("정지당한 계정입니다.");
      //추가이후 예외처리
    } else if (!user.getRole().equals(UserRoleEnum.ADMIN)) {
      throw new RejectedExecutionException("권한이 없습니다");
    }

    // 핵심 기능 수행
    return joinPoint.proceed();
  }
}