package org.example.backend.service.useredit;

import org.example.backend.user.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserEditService {

    private static final Logger logger = LoggerFactory.getLogger(UserEditService.class);

    @Autowired
    private UserEditDao userEditDao;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public boolean changeName(int userId, String name) throws Exception {
        User user = userEditDao.select(userId);

        // Check if the user exists
        if (user != null) {
            // Update user name
            userEditDao.updateName(userId, name);
            return true;
        } else {
            return false;
        }
    }

    // 사용자 비밀번호를 변경하는 메서드
    public User changePassword(int userId, String oldPassword, String newPassword) throws Exception {
        User user = userEditDao.select(userId);

        // 데이터베이스에서 조회한 사용자의 인코딩된 비밀번호
        String encodedPasswordFromDatabase = user.getPassword();

        // 기존 비밀번호가 일치하는지 확인
        if (passwordEncoder.matches(oldPassword, encodedPasswordFromDatabase)) {
            // 새로운 비밀번호를 인코딩
            String encodedNewPassword = passwordEncoder.encode(newPassword);

            // 사용자 객체에 새로운 비밀번호 설정
            user.setPassword(encodedNewPassword);

            // 사용자 정보를 업데이트
            userEditDao.updatePass(user);
            return user; // 업데이트된 사용자 객체 반환
        } else {
            throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다."); // 기존 비밀번호가 일치하지 않음
        }
    }
}


//package org.example.backend.service.useredit;
//
//import org.example.backend.user.dto.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserEditService {
//
//    @Autowired
//    private UserEditDao userEditDao;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    // 사용자 비밀번호를 변경하는 메서드
//    public boolean changePassword(int userId, String oldPassword, String newPassword) throws Exception {
//        User user = userEditDao.select(userId);
//
//        // 데이터베이스에서 조회한 사용자의 인코딩된 비밀번호
//        String encodedPasswordFromDatabase = user.getPassword();
//
//        // 기존 비밀번호가 일치하는지 확인
//        if (passwordEncoder.matches(oldPassword, encodedPasswordFromDatabase)) {
//            // 새로운 비밀번호를 인코딩
//            String encodedNewPassword = passwordEncoder.encode(newPassword);
//
//            // 사용자 객체에 새로운 비밀번호 설정
//            user.setPassword(encodedNewPassword);
//
//            // 사용자 정보를 업데이트
//            userEditDao.update(user);
//            return true;
//        } else {
//            return false; // 기존 비밀번호가 일치하지 않음
//        }
//    }
//}

