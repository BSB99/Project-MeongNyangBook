package com.example.meongnyangbook.exception;

import com.example.meongnyangbook.common.ApiResponseDto;
import com.slack.api.Slack;
import com.slack.api.model.Attachment;
import com.slack.api.model.Field;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.slack.api.webhook.WebhookPayloads.payload;
import static org.hibernate.query.sqm.tree.SqmNode.log;

@ControllerAdvice
public class ErrorDetectAdvisor {
    private final Slack slackClient = Slack.getInstance();

    @Value("${slack.webhook.alertbot.url}")
    private String webhookUrl;

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseDto> unhandledException(IllegalArgumentException e, HttpServletRequest request) {
        log.error("UnhandledException: {} {} errMessage={}\n"
        );
        sendSlackAlertErrorLog(e, request); // 슬랙 알림 보내는 메서드
        return ResponseEntity.internalServerError()
                .body(new ApiResponseDto("일시적으로 접속이 원활하지 않습니다. 멍냥북 서비스 팀에 문의 부탁드립니다.", 500));
    }

    private void sendSlackAlertErrorLog(IllegalArgumentException e, HttpServletRequest request) {
        try {
            slackClient.send(webhookUrl, payload(p -> p
                    .text("서버 에러 발생! 백엔드 측의 빠른 확인 요망")
                    .attachments(
                            List.of(generateSlackAttachment(e, request))
                    )
            ));
        } catch (IOException slackError) {
            // slack 통신 시 발생한 예외에서 Exception을 던져준다면 재귀적인 예외가 발생
            log.debug("Slack 통신과의 예외 발생");
        }
    }

    private Attachment generateSlackAttachment(IllegalArgumentException e, HttpServletRequest request) {
        String requestTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(LocalDateTime.now());
        String xffHeader = request.getHeader("X-FORWARDED-FOR");  // 프록시 서버일 경우 client IP는 여기에 담김
        return Attachment.builder()
                .color("ff0000")  // 붉은 색
                .title(requestTime + " 발생 에러 로그")
                .fields(List.of(
                                generateSlackField("Request IP", xffHeader == null ? request.getRemoteAddr() : xffHeader),
                                generateSlackField("Request URL", request.getRequestURL() + " " + request.getMethod()),
                                generateSlackField("Error Message", e.getMessage())
                        )
                )
                .build();
    }

    private Field generateSlackField(String title, String value) {
        return Field.builder()
                .title(title)
                .value(value)
                .valueShortEnough(false)
                .build();
    }

}
