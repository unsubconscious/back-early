package org.example.backend.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportsUserDetailVo {
    int commentId;
    int commentAuthorId;
    String reportText;
    String content;
    String commentAuthorEmail;
    String reporterEmail;
}
