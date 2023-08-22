package com.example.meongnyangbook.report.dto;

import com.example.meongnyangbook.report.entity.ReportEnum;
import lombok.Getter;

@Getter
public class ReportRequestDto {
    private String msg;
    private ReportEnum reportEnum;
}
