package org.example.backend.service;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
//주문내역 조회를 위한 vo
public class OrderListVo {
    //주문번호
    private int orderId;
    //주문자 id
    private int customerId;
    //상점 id
    private int storeId;
    //주문내용
    private String orderDetails;
    //날짜
    private Timestamp orderDate;
    //총가격
    private int totalPrice;
    //가게 이름
    private String storeName;
    //이미지
    private String storeImage;
    //상태
    private int orderApprovalStatus;



}
