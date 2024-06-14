package org.example.backend.admin;

import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
public class AdminApproveVo {
    private int owner_id;
    private String store_name;
    private Timestamp modification_date;
    private int approval_status;
}
