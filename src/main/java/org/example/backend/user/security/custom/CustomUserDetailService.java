package org.example.backend.user.security.custom;

import org.example.backend.user.dto.CustomUser;
import org.example.backend.user.dto.User;
import org.example.backend.user.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
// 사용자 인증 방식 셋팅하기 위해 사용
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //loadUserByUsername() 설정을 해두면 Spring Security가 유저 정보를 읽어온다.
        log.info("login - loadUserByEmail : " + email);

        User user = userMapper.login(email); //mybatis로 DB 연결을 하고 있기 때문에 user의 id로 DTO에서 넘겨 받는 Users 객체를 조회한다.
        System.out.println(user.getUser_id());

        if(user == null) {
            log.info("일치하는 ID가 없습니다!");
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다 : " + email);
        }

        //예외를 빠져나왔을 경우에는 user id가 존재하는 것이기 때문에 로그를 찍어 user id를 확인 해준다.
        log.info("email (user의 실제 id) : ");
        log.info(user.toString());

        // User -> CustomUser
        CustomUser customUser = new CustomUser(user);

        log.info("customUser : ");
        log.info(customUser.toString());

        System.out.println("CustomUserDetailService완료");

        return customUser;
    }
}
