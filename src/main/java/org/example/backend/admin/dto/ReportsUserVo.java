package org.example.backend.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportsUserVo {
    int commentAuthorId;
    int countOfComments;
    String email;
}
