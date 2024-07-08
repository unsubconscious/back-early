package org.example.backend.user.service;

import jakarta.servlet.http.HttpServletRequest;
import org.example.backend.user.dto.User;
import org.example.backend.user.dto.UserAuth;
import org.example.backend.user.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.user.mapper.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service //서비스임을 알리는 어노테이션, 서비스로 빈이 등록되어 있어야 비즈니스 로직으로 사용가능하다.
//UserService의 실제 구현체 (클래스)
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder; //스프링시큐리티에 비밀번호 암호화 기능을 가져와서 자동주입 시킨다.

    @Autowired
    private UserRepository userMapper;
    //private UserMapper userMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    /* 회원 등록 (회원가입)
        1. 비밀번호 암호화
        2. 회원 등록
        3. 권한 등록
    */

    // <회원 : user>

    // 회원 조회
    @Override
    public User select(int user_id) throws Exception {
        return userMapper.select(user_id);
    }

    // 회원 가입 (등록)
    @Override
    public int insert(User user) throws Exception {
        try {
            // 비번 암호화
            String password = user.getPassword();
            String encodedPw = passwordEncoder.encode(password);
            user.setPassword(encodedPw);

            int result = userMapper.insert(user);

            if (result > 0) {
                UserAuth userAuth = new UserAuth();
                userAuth.setUserId(String.valueOf(user.getEmail()));
                userAuth.setAuth("ROLE_USER");
                result = userMapper.insertAuth(userAuth);
            }
            return result;

        } catch (DuplicateKeyException e){
            return -7;//이메일 중복이다

        }catch (Exception e) {
            // 일반적인 예외 처리
            System.err.println(e);
            // 필요한 경우 로그 기록이나 다른 처리
            throw e; // 필요에 따라 예외를 다시 던질 수도 있음
        }


    }

    // 로그인
    @Override
    public void login(User user, HttpServletRequest request) throws Exception {
        System.out.println("login 실행");
        String email = user.getEmail();
        String password = user.getPassword();
        log.info("eamil (user의 실제 id) : " + email);
        log.info("password : " + password);

        // AuthenticationManager (인증을 관리하는 객체) : 이것을 사용하면 id, pw가 일치하는지 확인해주며 SpringSecurity Context에 등록을 시킬수가 있다.
        // ID, PW 인증 토큰을 생성
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);

        // 토큰에 요청 정보 등록
        token.setDetails(new WebAuthenticationDetails(request));//request 객체를 넣는 이유는 dto에서 넘어온 id, pw값이 실제로 요청 정보로 넘어온게 맞는지 확인시켜주는 작업을 하기 위해 사용한다.

        // 토큰을 이용한 인증 요청 - 로그인
        Authentication authentication = authenticationManager.authenticate(token); //토큰을 가지고 authenticationManager에 authenticate 메소드로 인증을 요청한다.

        log.info("인증 여부 : " + authentication.isAuthenticated());

        org.springframework.security.core.userdetails.User authUser = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        log.info("인증된 사용자 ID : " + authUser.getUsername());

        // 시큐리티 컨텍스트 인증된 사용자 등록 : 인증된 사용된 정보가 담겨야 하는 영역
        // 세션에 등록하여 사용하거나 시큐리티에 등록하려면
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    // <업체 : store>

    @Override
    public User select_store(int email) throws Exception {
        return userMapper.select(email);
    }

    @Override
    public int insert_store(User user) throws Exception {
        try {
            // 비번 암호화
            String password = user.getPassword();
            String encodedPw = passwordEncoder.encode(password);
            user.setPassword(encodedPw);

            int result = userMapper.insert(user);

            if (result > 0) {
                UserAuth userAuth = new UserAuth();
                userAuth.setUserId(String.valueOf(user.getEmail()));
                userAuth.setAuth("ROLE_STORE");
                result = userMapper.insertAuth(userAuth);
            }
            return result;

        } catch (DuplicateKeyException e){
            return -7;//이메일 중복이다

        }catch (Exception e) {
            // 일반적인 예외 처리
            System.err.println(e);
            // 필요한 경우 로그 기록이나 다른 처리
            throw e; // 필요에 따라 예외를 다시 던질 수도 있음
        }
    }


    @Override
    public void login_store(User user, HttpServletRequest request) throws Exception {
        String email = user.getEmail();
        String password = user.getPassword();
        log.info("eamil (업체 id) : " + email);
        log.info("password : " + password);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);

        token.setDetails(new WebAuthenticationDetails(request));

        Authentication authentication = authenticationManager.authenticate(token);

        log.info("인증 여부 : " + authentication.isAuthenticated());

        org.springframework.security.core.userdetails.User authUser = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        log.info("인증된 사용자 ID : " + authUser.getUsername());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    // <관리자 : admin>

    @Override
    public User select_admin(int email) throws Exception {
        return userMapper.select(email);
    }

    @Override
    public int insert_admin(User user) throws Exception {
        //비번 암호화
        String password = user.getPassword();
        String encodedPw = passwordEncoder.encode(password);
        user.setPassword(encodedPw);

        //업체 (회원) 등록
        int result = userMapper.insert(user);

        //권한 등록
        if(result > 0) {
            UserAuth userAuth = new UserAuth();
            userAuth.setUserId(String.valueOf(user.getEmail())); //String.valueOf를 쓰면 다양한 데이터 값을 문자열로 변환 할 수 있음.
            userAuth.setAuth("ROLE_ADMIN"); //기본 권한 : 사용자 권한(ROLE_USER)
            result = userMapper.insertAuth(userAuth);
        }
        return result;
    }

    @Override
    public void login_admin(User user, HttpServletRequest request) throws Exception {
        String email = user.getEmail();
        String password = user.getPassword();
        log.info("eamil (관리자 id) : " + email);
        log.info("password : " + password);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);

        token.setDetails(new WebAuthenticationDetails(request));

        Authentication authentication = authenticationManager.authenticate(token);

        log.info("인증 여부 : " + authentication.isAuthenticated());

        org.springframework.security.core.userdetails.User authUser = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        log.info("인증된 사용자 ID : " + authUser.getUsername());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    // <라이더 : Rider>

    @Override
    public User select_rider(int email) throws Exception {
        return userMapper.select(email);
    }

    @Override
    public int insert_rider(User user) throws Exception {
        //비번 암호화
        String password = user.getPassword();
        String encodedPw = passwordEncoder.encode(password);
        user.setPassword(encodedPw);

        //업체 (회원) 등록
        int result = userMapper.insert(user);

        //권한 등록
        if(result > 0) {
            UserAuth userAuth = new UserAuth();
            userAuth.setUserId(String.valueOf(user.getEmail())); //String.valueOf를 쓰면 다양한 데이터 값을 문자열로 변환 할 수 있음.
            userAuth.setAuth("ROLE_RIDER"); //기본 권한 : 사용자 권한(ROLE_USER)
            result = userMapper.insertAuth(userAuth);
        }
        return result;
    }

    @Override
    public void login_rider(User user, HttpServletRequest request) throws Exception {
        String email = user.getEmail();
        String password = user.getPassword();
        log.info("eamil (라이더 id) : " + email);
        log.info("password : " + password);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);

        token.setDetails(new WebAuthenticationDetails(request));

        Authentication authentication = authenticationManager.authenticate(token);

        log.info("인증 여부 : " + authentication.isAuthenticated());

        org.springframework.security.core.userdetails.User authUser = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        log.info("인증된 사용자 ID : " + authUser.getUsername());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public String checkEmail(String email){
        int rs=0;
        try{
           rs=  userMapper.checkEmail(email);
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println(rs);
        if(rs>=1){
            return "false";
        }
        else{
            return "available";
        }

    }

//    //회원 정보 수정
//    @Override
//    public int update(User user) throws Exception {
//        //비밀번호 암호화
//        String userPw = user.getPassword();
//        String encodedPw = passwordEncoder.encode(userPw);
//        user.setPassword(encodedPw);
//
//        int result = userMapper.update(user);
//
//        return result;
//    }
//
//    //회원 삭제 (회원 탈퇴)
//    @Override
//    public int delete(String userId) throws Exception {
//        return userMapper.delete(userId);
//    }
}
