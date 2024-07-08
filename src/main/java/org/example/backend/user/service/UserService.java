package org.example.backend.user.service;
import jakarta.servlet.http.HttpServletRequest;
import org.example.backend.user.dto.User;

public interface UserService {
    // <회원 : user>

    //조회
    public User select(int userId) throws Exception;

    //등록
    public int insert(User user) throws Exception;

    //로그인
    public void login(User user, HttpServletRequest request) throws Exception;

    // <업체 : store>

    public User select_store(int userId) throws Exception;

    public int insert_store(User user) throws Exception;

    public void login_store(User user, HttpServletRequest request) throws Exception;

    // <관리자 : admin>

    public User select_admin(int userId) throws Exception;

    public int insert_admin(User user) throws Exception;

    public void login_admin(User user, HttpServletRequest request) throws Exception;

    // <라이더 : rider>

    public User select_rider(int userId) throws Exception;

    public int insert_rider(User user) throws Exception;

    public void login_rider(User user, HttpServletRequest request) throws Exception;

    public String checkEmail(String email) ;


//    //회원 수정
//    public int update(User user) throws Exception;

//    //회원 삭제
//    public int delete(String userId) throws Exception;
}