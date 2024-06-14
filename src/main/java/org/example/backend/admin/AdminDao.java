package org.example.backend.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 모든 상점 승인 정보 가져오기
    public List<AdminApproveVo> getAllApprovals() {
        String sql = "SELECT owner_id, store_name, modification_date, approval_status FROM admin_approve";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(AdminApproveVo.class));
    }
}
