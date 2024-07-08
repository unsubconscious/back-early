package org.example.backend.store.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
public class ReportsVo {
    private int reportId;
    private int commentId;
    private int commentAuthorId;
    private int reportStatus;
    private String reportText;
    private int reporterId;
    private Timestamp reportDate;


}