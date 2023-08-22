package com.example.meongnyangbook.alarm.entity;

import com.example.meongnyangbook.entity.TimeStamped;
import com.example.meongnyangbook.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "alarms")
public class Alarm extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "alarm_title", nullable = false)
    private String alarmTitle;

    @Column(name = "alarm_body", nullable = false)
    private String alarmBody;

    @Column(nullable = false)
    private String alarmSender;

    @ManyToOne
    @JoinColumn(name = "like_user")
    private User user;

    @Column(name = "alarm_category_enum", nullable = false)
    @Enumerated(value = EnumType.STRING)
    AlarmCategoryEnum alarmCategoryEnum;

    public Alarm(String alarmTitle, String alarmBody, String alarmSender, User user, AlarmCategoryEnum alarmCategoryEnum) {
        this.alarmTitle = alarmTitle;
        this.alarmBody = alarmBody;
        this.alarmSender = alarmSender;
        this.user = user;
        this.alarmCategoryEnum = alarmCategoryEnum;
    }
}