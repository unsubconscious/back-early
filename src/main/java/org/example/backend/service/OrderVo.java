package org.example.backend.service;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
public class OrderVo {
    private int orderId;
    private int customerId;
    private int storeId;
    private String orderDetails;
    private int totalPrice;
    private int orderApprovalStatus;
    private Timestamp orderDate;

}
