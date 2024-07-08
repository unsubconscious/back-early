package org.example.backend.service;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
public class StoreReportVo {

    private int reportId;
    private int orderId;
    private int storeId;
    private int reportStatus;
    private String reportText;
    private int reporterId;
    private Timestamp reportDate;
}
