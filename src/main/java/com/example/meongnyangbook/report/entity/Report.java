package com.example.meongnyangbook.report.entity;

import com.example.meongnyangbook.entity.TimeStamped;
import com.example.meongnyangbook.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_reports")
public class Report extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "report_category", nullable = false)
    private String reportCategory;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
