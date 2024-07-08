package org.example.backend.user.mapper;

import org.example.backend.user.dto.User;
import org.example.backend.user.dto.UserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<User> userRowMapper = new BeanPropertyRowMapper<>(User.class);

    private final RowMapper<Integer> emailRowMapper = new BeanPropertyRowMapper<>(Integer.class);
    private final RowMapper<UserAuth> userAuthRowMapper = new BeanPropertyRowMapper<>(UserAuth.class);

    // 회원 등록
    public int insert(User user) throws Exception {
        String sql = "INSERT INTO UserInformation (Email, Password, Name) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, user.getEmail(), user.getPassword(), user.getName());
    }

    // 회원 조회
    public User select(int user_id) throws Exception {
        String sql = "SELECT * FROM UserInformation WHERE user_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{user_id}, userRowMapper);
    }

    // 사용자 인증 (로그인) - email
    public User login(String email) {
        String sql = "SELECT u.user_id, u.email, u.password, u.name, u.registration_date, u.modification_date " +
                "FROM UserInformation u " +
                "LEFT OUTER JOIN UserInfo_Auth auth ON u.user_id = auth.user_id " +
                "WHERE u.email = ?";
        List<User> users = jdbcTemplate.query(sql, new Object[]{email}, userRowMapper);
        return users.isEmpty() ? null : users.get(0);
    }

    // 회원 권한 등록
    public int insertAuth(UserAuth userAuth) throws Exception {
        String sql = "INSERT INTO UserInfo_Auth (user_id, auth) VALUES (?, ?)";
        return jdbcTemplate.update(sql, userAuth.getUserId(), userAuth.getAuth());
    }

    public int checkEmail(String email) {
        String sql = "SELECT user_id FROM UserInformation WHERE Email = ?";
        return jdbcTemplate.queryForObject(sql,Integer.class,email);
    }

//    // 회원 수정
//    public int update(User user) throws Exception {
//        String sql = "UPDATE UserInformation SET email = ?, password = ?, name = ?, modification_date = CURRENT_TIMESTAMP WHERE user_id = ?";
//        return jdbcTemplate.update(sql, user.getEmail(), user.getPassword(), user.getName(), user.getUserId());
//    }
//
//    // 회원 삭제
//    public int delete(int user_id) throws Exception {
//        String sql = "DELETE FROM UserInformation WHERE user_id = ?";
//        return jdbcTemplate.update(sql, user_id);
//    }
}

