package org.example.backend.user.dto;

import lombok.Data;

//회원 권한
@Data
public class UserAuth {
    private int authNo;
    private String userId;
    private String auth;

    public UserAuth() {

    }

    public UserAuth(String userId, String auth) {
        this.userId = userId;
        this.auth = auth;
    }

    public UserAuth(int authNo,String userId, String auth) {
        this.authNo=authNo;
        this.userId = userId;
        this.auth = auth;
    }
}
