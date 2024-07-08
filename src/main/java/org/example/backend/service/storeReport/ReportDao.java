package org.example.backend.service.storeReport;

import org.example.backend.service.StoreReportVo;
import org.example.backend.store.dto.StoreRegistrationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ReportDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //신고했는지 아닌지확인하는 절차
    public int reportCheck(StoreReportVo storeReportVo){
        String sql = "SELECT store_id FROM StoreReports WHERE order_id = ? ;";
        //0을 리턴하면 아무것도 없다는거다.

        try{
            return jdbcTemplate.queryForObject(sql,Integer.class,storeReportVo.getOrderId());
        }
        catch (EmptyResultDataAccessException e){
            return 0;
        }
        catch (Exception e) {
            // 다른 예외 처리 로직 (예: 로깅)
            e.printStackTrace();
            return -1;
        }
    }

    public int report(StoreReportVo storeReportVo) {
        String sql = "INSERT INTO StoreReports ( order_id, store_id,reporter_id, report_text) " +
                "VALUES (?,?,?,?)";

        int rs = 0;
        try {
            jdbcTemplate.update(sql,storeReportVo.getOrderId(),storeReportVo.getStoreId(),storeReportVo.getReporterId(),storeReportVo.getReportText());
            rs = 1;
        } catch (Exception e) {
            e.printStackTrace();
            rs = -1;
        }

        return rs;
    }



}
