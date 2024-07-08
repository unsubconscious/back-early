package org.example.backend.service.search;

import org.example.backend.comments.dto.CommentsVo;
import org.example.backend.service.OrderListVo;
import org.example.backend.service.OrderVo;
import org.example.backend.store.dto.StoreInformationVo;
import org.example.backend.store.dto.StoreRegistrationVo;
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
        String sql="select * from StoreRegistration where store_ca=? AND approval_status=1 AND store_x BETWEEN ? - 0.08 AND ? + 0.08 AND store_y BETWEEN ? - 0.08 AND ? + 0.08";

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

    public String emailTrue(int id){

        String sql="select Email from userinformation where user_id=?";
        String email=null;

        try{
            email= jdbcTemplate.queryForObject(sql,String.class,id);


        }catch (Exception e){
            e.printStackTrace();

        }
        return email;
    }
    //주문내역 조회

    public List<OrderListVo> getUserOrders(int userId) {
        String sql = "SELECT oi.order_id,\n" +
                "       oi.customer_id,\n" +
                "       oi.store_id,\n" +
                "       oi.order_details,\n" +
                "       oi.total_price,\n" +
                "       oi.order_date,\n" +
                "       sr.store_name,\n" +
                "       sr.store_image,\n" +
                "       oi.order_approval_status\n"+
                "FROM OrderInformation oi\n" +
                "INNER JOIN StoreRegistration sr ON oi.store_id = sr.store_id\n" +
                "WHERE oi.customer_id = ? \n" +
                "ORDER BY oi.order_id DESC;";
        RowMapper<OrderListVo> rowMapper = BeanPropertyRowMapper.newInstance(OrderListVo.class);
        return jdbcTemplate.query(sql, rowMapper, userId);
    }

    //검색창에서 조회하기
    public List<StoreRegistrationVo> storeList2(BigDecimal x, BigDecimal y, String word) {
        String sql = "select * from StoreRegistration where approval_status = 1 " +
                "AND store_name LIKE CONCAT('%', ?, '%') " +
                "AND store_x BETWEEN ? - 0.08 AND ? + 0.08 " +
                "AND store_y BETWEEN ? - 0.08 AND ? + 0.08";

        List<StoreRegistrationVo> stores = new ArrayList<>();
        RowMapper<StoreRegistrationVo> rowMapper = BeanPropertyRowMapper.newInstance(StoreRegistrationVo.class);

        try {
            stores = jdbcTemplate.query(sql, rowMapper, word, x, x, y, y);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stores;
    }

    //ai 조회
    public List<StoreRegistrationVo> storeList3(BigDecimal x, BigDecimal y,String word) {
        String sql ="select * from storeregistration s\n" +
                "join storeinformation sf on sf.store_id =s.store_id\n" +
                "where sf.visibility_status = 1 and sf.menu_name LIKE CONCAT('%', ?, '%') AND store_x BETWEEN ? - 0.08 AND ? + 0.08 AND store_y BETWEEN ? - 0.08 AND ? + 0.08;";
        List<StoreRegistrationVo> stores = new ArrayList<>();
        RowMapper<StoreRegistrationVo> rowMapper = BeanPropertyRowMapper.newInstance(StoreRegistrationVo.class);

        try {
            return jdbcTemplate.query(sql,rowMapper,word,x,x,y,y);
        } catch (Exception e) {
            e.printStackTrace();
            return stores;
        }
    }

    //사용자 리뷰 목록 불러오기
    public List<CommentsVo> review(int id){

        String sql = "SELECT \n" +
                "    comment_id,\n" +
                "    store_id,\n" +
                "    author_id,\n" +
                "    author_name,\n" +
                "    content,\n" +
                "    rating,\n" +
                "    visibility_status,\n" +
                "creation_date,\n"+
                "    depth\n" +
                "FROM \n" +
                "    Comments\n" +
                "WHERE \n" +
                "    author_id = ? \n" +
                "    AND visibility_status IN (1, 2)\n" +
                "ORDER BY \n" +
                "    comment_id DESC;\n";
        List<CommentsVo> commentLists = new ArrayList<CommentsVo>();
        RowMapper<CommentsVo> rowMapper= BeanPropertyRowMapper.newInstance(CommentsVo.class);
        try {
            commentLists = jdbcTemplate.query(sql, rowMapper, id);
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return commentLists;

    }

}

