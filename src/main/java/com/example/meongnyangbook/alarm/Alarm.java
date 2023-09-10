package com.example.meongnyangbook.alarm;

import com.example.meongnyangbook.entity.TimeStamped;
import com.example.meongnyangbook.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

  @Column(nullable = false)
  private String body;

  @Column(nullable = false)
  private String sender;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "alarm_category_enum", nullable = false)
  @Enumerated(value = EnumType.STRING)
  AlarmCategoryEnum alarmCategoryEnum;

  public Alarm(String body, String sender, User user,
      AlarmCategoryEnum alarmCategoryEnum) {
    this.body = body;
    this.sender = sender;
    this.user = user;
    this.alarmCategoryEnum = alarmCategoryEnum;
  }
}