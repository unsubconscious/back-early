package org.example.backend.service;

import org.example.backend.store.StoreInformationVo;
import org.example.backend.store.StoreRegistrationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SearchDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    //음식점 조회 카테고리형식
//    SELECT *
//    FROM StoreRegistration
//    WHERE store_ca = ?
//    AND approval_status = 1
//    AND store_x BETWEEN ? - 0.05 AND ? + 0.05
//    AND store_y BETWEEN ? - 0.05 AND ? + 0.05;
    public List<StoreRegistrationVo> storeList(String num , BigDecimal x , BigDecimal y){
        String sql="select * from StoreRegistration where store_ca=? AND approval_status=1 AND store_x BETWEEN ? - 0.05 AND ? + 0.05 AND store_y BETWEEN ? - 0.05 AND ? + 0.05";

        List<StoreRegistrationVo> stoes = new ArrayList<StoreRegistrationVo>();
        RowMapper<StoreRegistrationVo> rowMapper = BeanPropertyRowMapper.newInstance(StoreRegistrationVo.class);
        try{
            stoes=jdbcTemplate.query(sql,rowMapper,num,x,x,y,y);


        }catch (Exception e){
        e.printStackTrace();

        }
                return stoes;

    }

//메뉴 목록 불러오기
    public List<StoreInformationVo> menuList(int id){
        String sql = "SELECT * FROM StoreInformation WHERE store_id = ?";
        List<StoreInformationVo> menus=new ArrayList<StoreInformationVo>();
        RowMapper<StoreInformationVo> rowMapper= BeanPropertyRowMapper.newInstance(StoreInformationVo.class);
        try {
            menus=jdbcTemplate.query(sql, rowMapper,id);
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return menus;
    }

    //주문하기
    public int order(OrderVo orderVo){
        String sql="INSERT INTO OrderInformation (customer_id, store_id, order_details,total_price,user_x,user_y) " +
                "VALUES (?,?,?,?,?,?)";
        int rs=-1;
        try{
            jdbcTemplate.update(sql,orderVo.getCustomerId(),orderVo.getStoreId(),orderVo.getOrderDetails(),orderVo.getTotalPrice(),orderVo.getUser_x(),orderVo.getUser_y());
            rs=1;

        }catch (Exception e){
            e.printStackTrace();

        }


        return rs;
    }

    //이메일 탐색
    public String email(int id){

        String sql="select Email from userinformation where user_id=?";
        String email=null;

        try{
            email= jdbcTemplate.queryForObject(sql,String.class,id);


        }catch (Exception e){
            e.printStackTrace();

        }
        return email;
    }

    public List<OrderVo> getUserOrders(int userId) {
        String sql = "SELECT * FROM orderinformation WHERE customer_id = ? ORDER BY orderId DESC";
        RowMapper<OrderVo> rowMapper = BeanPropertyRowMapper.newInstance(OrderVo.class);
        return jdbcTemplate.query(sql, rowMapper, userId);
    }

}
