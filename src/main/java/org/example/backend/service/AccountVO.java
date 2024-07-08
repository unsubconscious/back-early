package org.example.backend.service;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class AccountVO {
    private int accountId;

    private int ownerId;

    private String ownerName;

    private String ownerEmail;

    private int accountStatus;

    private Integer accountAmount;

    private Timestamp approvalDate;

    private Timestamp changeDate;

}
