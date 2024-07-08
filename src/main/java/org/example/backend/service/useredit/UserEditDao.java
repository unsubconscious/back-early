package org.example.backend.service.useredit;

import org.example.backend.user.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserEditDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<User> userRowMapper = new BeanPropertyRowMapper<>(User.class);

    // 사용자 정보를 userId로 조회하는 메서드
    public User select(int userId) throws Exception {
        String sql = "SELECT * FROM UserInformation WHERE user_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{userId}, userRowMapper);
    }

    // 사용자 이름을 업데이트하는 메서드
    public int updateName(int userId, String nickname) {
        String sql = "UPDATE UserInformation SET name = ? WHERE user_id = ?";
        return jdbcTemplate.update(sql, nickname, userId);
    }

    // 사용자 비밀번호를 업데이트하는 메서드
    public int updatePass(User user) {
        String sql = "UPDATE UserInformation SET password = ?, modification_date = CURRENT_TIMESTAMP WHERE user_id = ?";
        return jdbcTemplate.update(sql, user.getPassword(), user.getUser_id());
    }
}