package org.example.backend.user.service;
import jakarta.servlet.http.HttpServletRequest;
import org.example.backend.user.dto.User;

public interface UserService {
    //회원 등록
    public int insert(User user) throws Exception;

    //회원 조회
    public User select(int email) throws Exception;

    //로그인
    public void login(User user, HttpServletRequest request) throws Exception;

//    //회원 수정
//    public int update(User user) throws Exception;

//    //회원 삭제
//    public int delete(String userId) throws Exception;
}
