package org.example.backend.store;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
public class StoreRegistrationVo {

    private int store_id;
    private int owner_id;
    private String store_name;
    private String store_address;
    private String store_description;
    private String store_image;
    private int approval_status;
    private Date approval_date;
    private Timestamp modification_date;
    private BigDecimal store_x;
    private BigDecimal store_y;
    private String store_ca;
}
