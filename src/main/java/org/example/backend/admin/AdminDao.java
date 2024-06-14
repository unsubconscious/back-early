package org.example.backend.admin;

import org.example.backend.store.StoreInformationVo;
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
    public List<AdminApproveVo> postAllApprovals() {
        String sql = "SELECT owner_id, store_name, modification_date, approval_status FROM admin_approve";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(AdminApproveVo.class));
    }

    public int adminApprovalupdate(int owner_id){

        String sql = "UPDATE admin_approve SET approval_status = 1 WHERE owner_id = ?";

        try {
            return jdbcTemplate.update(sql, owner_id);
        } catch (Exception e) {
            // 예외 처리 로직 (예: 로깅)
            e.printStackTrace();
            return -1;
        }
    }

//    메뉴수정
//    public int menuedit(StoreInformationVo storeInformationVo){
//        String sql = "UPDATE StoreInformation SET menu_price = ? , menu_image = ? WHERE store_id = ? AND menu_name = ?";
//
//        int rs=-1;
//        try {
//            return jdbcTemplate.update(sql,storeInformationVo.getMenuPrice(),storeInformationVo.getMenuImage(),storeInformationVo.getStoreId(),storeInformationVo.getMenuName());
//        } catch (Exception e) {
//            // 예외 처리 로직 (예: 로깅)
//            e.printStackTrace();
//            return -1;
//        }
//
//    }

}
