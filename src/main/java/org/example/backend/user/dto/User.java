package org.example.backend.user.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data // Getter, Setter, toString(), equals(), hashCode() 메서드를 자동으로 생성 해주는 어노테이션
//user 테이블과 매핑시킬 DTO
public class User {

  private int user_id; //자동으로 증가될 pk 값
  private String email; //user의 id
  private String password; //user의 비번
  private String name; //user명
  private Date registration_date; //등록된 날짜
  private Date modification_date; //수정한 날짜

    // 권한 목록
    List<UserAuth> authList;

    //기본 생성자 생성
    public User() {

    }

    //필요한 생성자
    public User(User user) {
        this.user_id = user.getUser_id();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
        this.authList = user.getAuthList();
    }
}
