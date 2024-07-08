package org.example.backend.admin;

import org.example.backend.admin.dto.*;
import org.example.backend.service.StoreReportVo;
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
                "WHERE order_approval_status IN (4, 6)";
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
                "WHERE order_approval_status IN (4, 6)";

        List<AdminOrderInformationVo> orderSales = new ArrayList<AdminOrderInformationVo>();
        RowMapper<AdminOrderInformationVo> rowMapper= BeanPropertyRowMapper.newInstance(AdminOrderInformationVo.class);
        try {
            orderSales = jdbcTemplate.query(sql, rowMapper);
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return orderSales;
    }

    //유저 신고 내역 조회


    public List<ReportsUserVo> userReport(){
        String sql = "SELECT \n" +
                "    r.comment_author_id,\n" +
                "    u.Email,\n" +
                "    COUNT(*) AS count_of_comments\n" +
                "FROM \n" +
                "    Reports r\n" +
                "JOIN \n" +
                "    UserInformation u ON r.comment_author_id = u.user_id\n" +
                "JOIN \n" +
                "    userinfo_auth ua ON u.Email = ua.user_id\n" +
                "WHERE \n" +
                "    ua.auth = 'ROLE_USER'\n" +
                "GROUP BY \n" +
                "    r.comment_author_id, u.Email\n" +
                "HAVING \n" +
                "    COUNT(*) >= 2;\n";

        List<ReportsUserVo> userReports = new ArrayList<ReportsUserVo>();
        RowMapper<ReportsUserVo> rowMapper= BeanPropertyRowMapper.newInstance(ReportsUserVo.class);
        try {
            userReports = jdbcTemplate.query(sql, rowMapper);
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return userReports;
    }

    public List<ReportsUserDetailVo> userDetail(int authorId){
        String sql = "SELECT \n" +
                "    R.comment_id,\n" +
                "    R.comment_author_id,\n" +
                "    R.report_text,\n" +
                "    R.reporter_id,\n" +
                "    C.content,\n" +
                "    UA.Email AS comment_author_email,\n" +
                "    UR.Email AS reporter_email\n" +
                "FROM Reports R\n" +
                "JOIN UserInformation UA ON R.comment_author_id = UA.user_id\n" +
                "JOIN UserInformation UR ON R.reporter_id = UR.user_id\n" +
                "JOIN comments C ON R.comment_id = C.comment_id\n" +
                "JOIN userinfo_auth UA_AUTH ON UA.Email = UA_AUTH.user_id\n" +
                "WHERE R.comment_author_id = ?\n;";

        List<ReportsUserDetailVo> userDetails = new ArrayList<ReportsUserDetailVo>();
        RowMapper<ReportsUserDetailVo> rowMapper= BeanPropertyRowMapper.newInstance(ReportsUserDetailVo.class);
        try {
            userDetails = jdbcTemplate.query(sql, rowMapper,authorId);
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return userDetails;
    }

    //유저 블락하기
    public int block(int id){

        String sql = "UPDATE userinfo_auth ua\n" +
                "JOIN UserInformation ui ON ua.user_id = ui.Email\n" +
                "SET ua.auth = 'USER_BLOCK'\n" +
                "WHERE ui.user_id = ?;";

        try {
            jdbcTemplate.update(sql,id);
            return 1;
        } catch (Exception e) {
            // 예외 처리 로직 (예: 로깅)
            e.printStackTrace();
            return -1;
        }
    }

    //유저쪽에서 업체 신고한 조회
    public List<ReportStoreVo> storeReport(){
        String sql ="SELECT \n" +
                "    r.store_id,\n" +
                "    u.store_name,\n" +
                "    COUNT(*) AS count\n" +
                "FROM \n" +
                "    storereports r\n" +
                "JOIN \n" +
                "    storeregistration u ON r.store_id = u.store_id\n" +
                "WHERE \n" +
                "    u.approval_status = 1\n" +
                "GROUP BY \n" +
                "    r.store_id, u.store_name\n" +
                "HAVING \n" +
                "    COUNT(*) >= 2;";

        List<ReportStoreVo> userReports = new ArrayList<ReportStoreVo>();
        RowMapper<ReportStoreVo> rowMapper= BeanPropertyRowMapper.newInstance(ReportStoreVo.class);
        try {
            userReports = jdbcTemplate.query(sql, rowMapper);
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return userReports;
    }

    public List<ReportStoreDetailVo> storeDetail(int storeId){
        String sql = "SELECT \n" +
                "    SR.report_id,\n" +
                "    SR.order_id,\n" +
                "    SR.store_id,\n" +
                "    SREG.store_name,\n" +
                "    SR.report_status,\n" +
                "    SR.report_text,\n" +
                "    SR.reporter_id,\n" +
                "    UI.Email AS reporter_email,\n" +
                "    SR.report_date\n" +
                "FROM \n" +
                "    StoreReports SR\n" +
                "JOIN \n" +
                "    StoreRegistration SREG ON SR.store_id = SREG.store_id\n" +
                "JOIN \n" +
                "    UserInformation UI ON SR.reporter_id = UI.user_id\n" +
                "WHERE \n" +
                "    SR.store_id = ?;";

        List<ReportStoreDetailVo> userDetails = new ArrayList<ReportStoreDetailVo>();
        RowMapper<ReportStoreDetailVo> rowMapper= BeanPropertyRowMapper.newInstance(ReportStoreDetailVo.class);
        try {
            userDetails = jdbcTemplate.query(sql, rowMapper,storeId);
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return userDetails;
    }

    //스토어 블락하기
    public int Storeblockblock(int id){

        String sql = "    UPDATE storeregistration set approval_status=2\n" +
                "\tWHERE store_id = ? ;\n";

        try {
            jdbcTemplate.update(sql,id);
            return 1;
        } catch (Exception e) {
            // 예외 처리 로직 (예: 로깅)
            e.printStackTrace();
            return -1;
        }
    }

    //업체 신고 리포터 1로 변경






}
