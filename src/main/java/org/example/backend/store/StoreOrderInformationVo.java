package org.example.backend.store;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class StoreOrderInformationVo {
    private int order_id;
    private int customer_id;
    private int store_id;
    private String order_details;
    private int total_price;
    private int user_x;
    private int user_y;
    private String email;
    private String name;
    private int order_approval_status; //주문 승인 상태
    private Timestamp order_date; //주문일
}
