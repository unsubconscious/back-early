package org.example.backend.admin;

import org.example.backend.store.StoreInformationVo;
import org.example.backend.store.StoreOrderInformationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AdminDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 모든 상점 승인 정보 가져오기
    public List<AdminApproveVo> postAllApprovals() {
        String sql = "SELECT owner_id, store_name, modification_date, approval_status FROM StoreRegistration";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(AdminApproveVo.class));
    }

    public int adminApprovalupdate(int owner_id){

        String sql = "UPDATE StoreRegistration SET approval_status = 1 WHERE owner_id = ?";

        try {
            return jdbcTemplate.update(sql, owner_id);
        } catch (Exception e) {
            // 예외 처리 로직 (예: 로깅)
            e.printStackTrace();
            return -1;
        }
    }

    //관리자 결제 내역 불러오기
    public List<AdminOrderInformationVo> orderReceipt(){
        String sql = "SELECT o.order_id, o.customer_id, o.store_id, o.order_details, o.total_price, o.user_x, o.user_y, " +
                "u.Email AS email, u.Name AS name " +
                "FROM OrderInformation o " +
                "JOIN UserInformation u ON o.customer_id = u.user_id " +
                "WHERE order_approval_status = 4";
        List<AdminOrderInformationVo> order_info = new ArrayList<AdminOrderInformationVo>();
        RowMapper<AdminOrderInformationVo> rowMapper= BeanPropertyRowMapper.newInstance(AdminOrderInformationVo.class);
        try {
            order_info = jdbcTemplate.query(sql, rowMapper);
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return order_info;
    }

    //현재 매출 내역 조회 (현재 매출 내역을 확인할 표에서 수행될 기능)
    public List<AdminOrderInformationVo> ManagerRevenue(int order_approval_status){
        String sql = "SELECT order_details, total_price, order_date " +
                "FROM OrderInformation " +
                "WHERE order_approval_status = ?";

        List<AdminOrderInformationVo> orderSales = new ArrayList<AdminOrderInformationVo>();
        RowMapper<AdminOrderInformationVo> rowMapper= BeanPropertyRowMapper.newInstance(AdminOrderInformationVo.class);
        try {
            orderSales = jdbcTemplate.query(sql, rowMapper,order_approval_status);
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return orderSales;
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
