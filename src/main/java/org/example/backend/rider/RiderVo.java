package org.example.backend.rider;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RiderVo {
    private int deliveryId;
    private int orderId;
    private int storeId;
    private String storeName;
    private String storeOwnerEmail;
    private int riderId;
    private double distanceToStore;
    private double distanceToUser;
    private int deliveryPrice;
    private int deliverystatus;
    private BigDecimal user_x;
    private BigDecimal user_y;
    private BigDecimal store_x;
    private BigDecimal store_y;
}
