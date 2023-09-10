package com.example.meongnyangbook.aop;

import com.example.meongnyangbook.post.adoptionPost.AdoptionPost;
import com.example.meongnyangbook.post.adoptionPost.AdoptionPostService;
import com.example.meongnyangbook.post.communityPost.CommunityPost;
import com.example.meongnyangbook.post.communityPost.CommunityPostService;
import com.example.meongnyangbook.shop.inquiry.Inquiry;
import com.example.meongnyangbook.shop.inquiry.InquiryService;
import com.example.meongnyangbook.shop.review.ReviewService;
import java.util.concurrent.RejectedExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j(topic = "RoleCheckAop")
@Component
@Aspect
public class RoleCheckAop {

  @Autowired
  private AdoptionPostService adoptionPostService;

  @Autowired
  private CommunityPostService communityPostService;

  @Autowired
  private InquiryService inquiryService;

  @Autowired
  private ReviewService reviewService;

  @Pointcut("execution(* com.example.meongnyangbook.post.adoptionPost.AdoptionPostController.updateAdoption(..))")
  private void updateAdoption() {
  }

  @Pointcut("execution(* com.example.meongnyangbook.post.adoptionPost.AdoptionPostController.deleteAdoption(..))")
  private void deleteAdoption() {
  }

  @Pointcut("execution(* com.example.meongnyangbook.post.communityPost.CommunityPostController.updateCommunity(..))")
  private void updateCommunity() {
  }

  @Pointcut("execution(* com.example.meongnyangbook.post.communityPost.CommunityPostController.deleteCommunity(..))")
  private void deleteCommunity() {
  }

  @Pointcut("execution(* com.example.meongnyangbook.shop.inquiry.InquiryController.updateInquiry(..))")
  private void updateInquiry() {
  }

  @Pointcut("execution(* com.example.meongnyangbook.shop.inquiry.InquiryController.deleteInquiry(..))")
  private void deleteInquiry() {
  }

  @Pointcut("execution(* com.example.meongnyangbook.shop.review.ReviewController.updateReview(..))")
  private void updateReview() {
  }

  @Pointcut("execution(* com.example.meongnyangbook.shop.review.ReviewController.deleteReview(..))")
  private void deleteReview() {
  }

  @Around("updateAdoption() || deleteAdoption()")
  public Object executePostAdoptionRoleCheck(ProceedingJoinPoint joinPoint) throws Throwable {
    // 1, 2번째 매개변수로 id, user값 가져오기
    Long AdoptionId = (Long) joinPoint.getArgs()[0];
    UserDetails user = (UserDetails) joinPoint.getArgs()[1];

    // 타겟 메서드에서 post 객체 가져오기
    AdoptionPost adoptionPost = adoptionPostService.getAdoption(AdoptionId);

    if (!adoptionPost.getUser().getUsername().equals(user.getUsername())) {
      throw new RejectedExecutionException("게시물 수정/삭제 권한없습니다");
    }

    // 핵심 기능 수행
    return joinPoint.proceed();
  }

  @Around("updateCommunity() || deleteCommunity()")
  public Object executePostCommunityRoleCheck(ProceedingJoinPoint joinPoint) throws Throwable {
    // 1, 2번째 매개변수로 id, user값 가져오기
    Long CommunityId = (Long) joinPoint.getArgs()[0];
    UserDetails user = (UserDetails) joinPoint.getArgs()[1];

    // 타겟 메서드에서 post 객체 가져오기
    CommunityPost communityPost = communityPostService.getCommunity(CommunityId);

    if (!communityPost.getUser().getUsername().equals(user.getUsername())) {
      throw new RejectedExecutionException("게시물 수정/삭제 권한이 없습니다");
    }

    // 핵심 기능 수행
    return joinPoint.proceed();
  }

  @Around("updateInquiry() || deleteInquiry()")
  public Object executeInquiryRoleCheck(ProceedingJoinPoint joinPoint) throws Throwable {
    // 1, 2번째 매개변수로 id, user값 가져오기
    Long inquiryId = (Long) joinPoint.getArgs()[0];
    UserDetails user = (UserDetails) joinPoint.getArgs()[1];

    // 타겟 메서드에서 post 객체 가져오기
    Inquiry inquiry = inquiryService.getInquiry(inquiryId);

    if (!inquiry.getUser().getUsername().equals(user.getUsername())) {
      throw new RejectedExecutionException("게시물 수정/삭제 권한이 없습니다");
    }
    // 핵심 기능 수행
    return joinPoint.proceed();
  }


}