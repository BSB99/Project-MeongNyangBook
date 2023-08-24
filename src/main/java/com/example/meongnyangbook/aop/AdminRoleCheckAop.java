package com.example.meongnyangbook.aop;

import com.example.meongnyangbook.user.details.UserDetailsImpl;
import com.example.meongnyangbook.user.entity.UserRoleEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.concurrent.RejectedExecutionException;

@Slf4j(topic = "AdminRoleCheckAop")
@Component
@Aspect
public class AdminRoleCheckAop {

    @Pointcut("execution(* com.example.meongnyangbook.report.controller.ReportController.deleteReport(..))")
    private void deleteReport() {}

    @Pointcut("execution(* com.example.meongnyangbook.report.controller.ReportController.getReports(..))")
    private void getReports() {}

    @Pointcut("execution(* com.example.meongnyangbook.backoffice.notification.controller.NoticeController.createNotice(..))")
    private void createNotice() {};

    @Pointcut("execution(* com.example.meongnyangbook.backoffice.notification.controller.NoticeController.getNotices(..))")
    private void getNotices() {};

    @Pointcut("execution(* com.example.meongnyangbook.backoffice.notification.controller.NoticeController.updateNotice(..))")
    private void updateNotice() {};

    @Pointcut("execution(* com.example.meongnyangbook.backoffice.notification.controller.NoticeController.deleteNotice(..))")
    private void deleteNotice() {};

    @Pointcut("execution(* com.example.meongnyangbook.shop.item.controller.ItemController.deleteItem(..))")
    private void deleteItem() {};

    @Pointcut("execution(* com.example.meongnyangbook.shop.item.controller.ItemController.createItem(..))")
    private void createItem() {};

    @Pointcut("execution(* com.example.meongnyangbook.shop.item.controller.ItemController.updateItem(..))")
    private void updateItem() {};

    @Around("deleteReport() || getReports() || createNotice() || getNotices() || updateNotice() || deleteNotice() || createItem() || updateItem() || deleteItem()")
    public Object executeReportRoleCheck(ProceedingJoinPoint joinPoint) throws Throwable {

        UserDetailsImpl user = (UserDetailsImpl) joinPoint.getArgs()[0];

        if (!user.getRole().equals(UserRoleEnum.ADMIN)) {
            throw new RejectedExecutionException("권한이 없습니다");
        }

        // 핵심 기능 수행
        return joinPoint.proceed();
    }
}