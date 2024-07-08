package org.example.backend.rider;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RiderDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //배달전체 목록 불러오기
    public List<RiderVo> orderlist(BigDecimal x, BigDecimal y){

        String sql = "SELECT \n" +
                "    o.order_id,\n" +
                "    o.store_id,\n" +
                "    s.store_name,\n" +
                "    u.Email AS store_owner_email,\n" +
                "    o.user_x,\n" +
                "    o.user_y,\n" +
                "    s.store_x,\n" +
                "    s.store_y\n" +
                "FROM \n" +
                "    OrderInformation o\n" +
                "JOIN \n" +
                "    StoreRegistration s ON o.store_id = s.store_id\n" +
                "JOIN \n" +
                "    UserInformation u ON s.owner_id = u.user_id\n" +
                "WHERE  o.order_approval_status = 2 AND s.store_x BETWEEN ? - 0.1 AND ? + 0.1 AND s.store_y BETWEEN ? - 0.1 AND ? + 0.1;";
        List<RiderVo> riderVos=new ArrayList<RiderVo>();
        RowMapper<RiderVo> rowMapper= BeanPropertyRowMapper.newInstance(RiderVo.class);
        try {
            riderVos=jdbcTemplate.query(sql, rowMapper,x,x,y,y);
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return riderVos;
    }

    //콜받은거 라이더 db에 등록하기
    public int call( RiderVo riderVo){
            String sql ="INSERT INTO RiderDelivery (order_id,store_id,store_name,store_owner_email,rider_id,distance_to_store,distance_to_user,delivery_price,store_x,store_y,user_x,user_y) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            int rs=-1;
            try {
                 jdbcTemplate.update(sql,riderVo.getOrderId(),riderVo.getStoreId(),riderVo.getStoreName(),riderVo.getStoreOwnerEmail(),riderVo.getRiderId(),riderVo.getDistanceToStore(),riderVo.getDistanceToStore(),riderVo.getDeliveryPrice(),riderVo.getStore_x(),riderVo.getStore_y(),riderVo.getUser_x(),riderVo.getUser_y());
                rs=1;

            } catch (Exception e) {
                // 예외 처리 로직 (예: 로깅)
                e.printStackTrace();
                return -1;
            }

        return rs;
        }



    public int order( RiderVo riderVo){
        String sql ="UPDATE orderinformation SET order_approval_status = 3 WHERE order_id = ?";
        int rs=-1;
        try {
            jdbcTemplate.update(sql,riderVo.getOrderId());
            return 1;
        } catch (Exception e) {
            // 예외 처리 로직 (예: 로깅)
            e.printStackTrace();
            return -1;
        }


    }

    //콜수락한거 목록조회
    public List<RiderVo> orderCall(int id){


        String sql = "select * from RiderDelivery where rider_id=? and delivery_status=0";
        List<RiderVo> riderVos=new ArrayList<RiderVo>();
        RowMapper<RiderVo> rowMapper= BeanPropertyRowMapper.newInstance(RiderVo.class);
        try {
            riderVos=jdbcTemplate.query(sql, rowMapper,id);
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return riderVos;
    }

    //배달 완료
    public int finish( RiderVo riderVo){
        String sql ="UPDATE RiderDelivery SET delivery_status = 1 WHERE delivery_id = ?";
        int rs=-1;
        try {
            jdbcTemplate.update(sql,riderVo.getDeliveryId());
            rs=1;

        } catch (Exception e) {
            // 예외 처리 로직 (예: 로깅)
            e.printStackTrace();
            return -1;
        }

        return rs;
    }

    public int orderfinish( RiderVo riderVo){
        String sql ="UPDATE orderinformation SET order_approval_status = 4 WHERE order_id = ?";
        int rs=-1;
        try {
            jdbcTemplate.update(sql,riderVo.getOrderId());
            return 1;
        } catch (Exception e) {
            // 예외 처리 로직 (예: 로깅)
            e.printStackTrace();
            return -1;
        }

    }

    public List<RiderVo> riderReceipt( int riderId) {
        String sql = "select * from RiderDelivery where rider_id=? and delivery_status=1";
        List<RiderVo> riderVos = new ArrayList<RiderVo>();
        RowMapper<RiderVo> rowMapper = BeanPropertyRowMapper.newInstance(RiderVo.class);
        try {
            riderVos = jdbcTemplate.query(sql, rowMapper, riderId);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return riderVos;
    }

    public List<RiderVo> riderRevenue(int riderId){
        String sql = "SELECT rider_id, delivery_price, delivery_id, order_date  " +
                "FROM RiderDelivery " +
                "WHERE rider_id = ? and delivery_status=1";

        List<RiderVo> Revenue = new ArrayList<RiderVo>();
        RowMapper<RiderVo> rowMapper= BeanPropertyRowMapper.newInstance(RiderVo.class);
        try {
            Revenue = jdbcTemplate.query(sql, rowMapper,riderId);
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return Revenue;
    }

}
