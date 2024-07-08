package org.example.backend.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
public class ReportStoreDetailVo {




    int reportId;
    int orderId;
    int storeId;
    int reporterId;
    int reportStatus;
    String storeName;
    String reportText;
    String reporterEmail;
    private Timestamp reportDate;

}
