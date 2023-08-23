package com.example.meongnyangbook.backoffice.notification.entity;

import com.example.meongnyangbook.backoffice.notification.dto.NoticeRequestDto;
import com.example.meongnyangbook.entity.TimeStamped;
import com.example.meongnyangbook.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "notices")
public class Notice extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    public Notice(NoticeRequestDto requestDto) {
        this.description = requestDto.getDescription();
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
