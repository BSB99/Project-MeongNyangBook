package com.example.meongnyangbook.aop;

import com.example.meongnyangbook.post.adoption.entity.Adoption;
import com.example.meongnyangbook.post.adoption.service.AdoptionService;
import com.example.meongnyangbook.post.community.entity.Community;
import com.example.meongnyangbook.post.community.service.CommunityService;
import com.example.meongnyangbook.user.details.UserDetailsImpl;
import com.example.meongnyangbook.user.entity.UserRoleEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.concurrent.RejectedExecutionException;

@Slf4j(topic = "RoleCheckAop")
@Component
@Aspect
public class RoleCheckAop {

    @Autowired
    private AdoptionService adoptionService;

    @Autowired
    private CommunityService communityService;

    @Pointcut("execution(* com.example.meongnyangbook.post.adoption.controller.AdoptionController.updateAdoption(..))")
    private void updateAdoption() {}

    @Pointcut("execution(* com.example.meongnyangbook.post.adoption.controller.AdoptionController.deleteAdoption(..))")
    private void deleteAdoption() {}

    @Pointcut("execution(* com.example.meongnyangbook.post.community.controller.CommunityController.updateCommunity(..))")
    private void updateCommunity() {}

    @Pointcut("execution(* com.example.meongnyangbook.post.community.controller.CommunityController.deleteCommunity(..))")
    private void deleteCommunity() {}

    @Pointcut("execution(* com.example.meongnyangbook.report.controller.ReportController.deleteReport(..))")
    private void deleteReport() {}

    @Pointcut("execution(* com.example.meongnyangbook.report.controller.ReportController.getReports(..))")
    private void getReports() {}

    @Around("updateAdoption() || deleteAdoption()")
    public Object executePostAdoptionRoleCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        // 1, 2번째 매개변수로 id, user값 가져오기
        Long AdoptionId = (Long) joinPoint.getArgs()[0];
        UserDetails user = (UserDetails) joinPoint.getArgs()[1];

        // 타겟 메서드에서 post 객체 가져오기
        Adoption adoption = adoptionService.getAdoption(AdoptionId);

        if(!adoption.getUser().getUsername().equals(user.getUsername())) {
            throw new RejectedExecutionException("게시물 삭제 권한없습니다");
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
        Community community = communityService.getCommunity(CommunityId);

        if(!community.getUser().getUsername().equals(user.getUsername())) {
            throw new RejectedExecutionException("게시물 삭제 권한없습니다");
        }

        // 핵심 기능 수행
        return joinPoint.proceed();
    }

    @Around("deleteReport() || getReports()")
    public Object executeReportRoleCheck(ProceedingJoinPoint joinPoint) throws Throwable {

        UserDetailsImpl user = (UserDetailsImpl) joinPoint.getArgs()[0];

        if (!user.getRole().equals(UserRoleEnum.ADMIN)) {
            throw new RejectedExecutionException("신고 관련 권한이 없습니다");
        }

        // 핵심 기능 수행
        return joinPoint.proceed();
    }
}
