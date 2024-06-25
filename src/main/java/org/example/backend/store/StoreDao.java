package org.example.backend.store;

import org.example.backend.service.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StoreDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    //상점등록

    public int storeInsert(StoreRegistrationVo storeRegistrationVo) {
        String sql = "INSERT INTO StoreRegistration (owner_id, store_name, store_address,store_description, store_image, store_x,store_y,store_ca) " +
                "VALUES ( ?, ?, ?,?,?,?,?,?)";

        int rs = 0;
        try {
            jdbcTemplate.update(sql, storeRegistrationVo.getOwner_id(), storeRegistrationVo.getStore_name(), storeRegistrationVo.getStore_address(), storeRegistrationVo.getStore_description()
                    , storeRegistrationVo.getStore_image(), storeRegistrationVo.getStore_x(), storeRegistrationVo.getStore_y(), storeRegistrationVo.getStore_ca());
            rs = 1;
        } catch (Exception e) {
            e.printStackTrace();
            rs = -1;
        }

        return rs;
    }

    //승인요청확인
    public int approveR(int id) {
        String sql = "SELECT store_id FROM StoreRegistration WHERE owner_id = ? AND approval_status = 1;";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, id);
        } catch (EmptyResultDataAccessException e) {
            // 결과가 없을 때 처리
            return -1;
        } catch (Exception e) {
            // 다른 예외 처리 로직 (예: 로깅)
            e.printStackTrace();
            return -1;
        }
    }


    //메뉴등록
    public int menuRs(StoreInformationVo storeInformationVo){
        String sql ="INSERT INTO StoreInformation (store_id, menu_name, menu_price,menu_image) " +
                "VALUES (?,?,?,?)";
        int rs=-1;
        try {
            return jdbcTemplate.update(sql,storeInformationVo.getStoreId(),storeInformationVo.getMenuName(),storeInformationVo.getMenuPrice(),storeInformationVo.getMenuImage());
        } catch (Exception e) {
            // 예외 처리 로직 (예: 로깅)
            e.printStackTrace();
            return -1;
        }


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

    //메뉴수정
    public int menuedit(StoreInformationVo storeInformationVo){
        String sql = "UPDATE StoreInformation SET menu_price = ? , menu_image = ? WHERE store_id = ? AND menu_name = ?";

        int rs=-1;
        try {
            return jdbcTemplate.update(sql,storeInformationVo.getMenuPrice(),storeInformationVo.getMenuImage(),storeInformationVo.getStoreId(),storeInformationVo.getMenuName());
        } catch (Exception e) {
            // 예외 처리 로직 (예: 로깅)
            e.printStackTrace();
            return -1;
        }


    }

    public int menuedit2(StoreInformationVo storeInformationVo){
        String sql = "UPDATE StoreInformation SET menu_price = ?  WHERE store_id = ? AND menu_name = ?";

        int rs=-1;
        try {
            return jdbcTemplate.update(sql,storeInformationVo.getMenuPrice(),storeInformationVo.getStoreId(),storeInformationVo.getMenuName());
        } catch (Exception e) {
            // 예외 처리 로직 (예: 로깅)
            e.printStackTrace();
            return -1;
        }


    }

    //메뉴삭제
    public int menudel(int id, String name){
        int rs=-1;
        String sql="delete from StoreInformation where store_id=? AND menu_name=?";
        try{
            jdbcTemplate.update(sql,id,name);
            rs=1;
            return rs;
        }
        catch (Exception e){
            e.printStackTrace();
            return rs;
        }


    }
    //1 조리중
    //2은 배달기사 배정중
    //3는 배달중
    //5은 거절
    //4 완료
    //주문알람
    public List<OrderVo> order(int id){

        System.out.println(id);
        String sql = "SELECT * FROM orderinformation " +
                "WHERE store_id = ? " +
                "AND order_approval_status NOT IN (4,5) " +
                "ORDER BY order_id DESC;";
        List<OrderVo> orders=new ArrayList<OrderVo>();
        RowMapper<OrderVo> rowMapper= BeanPropertyRowMapper.newInstance(OrderVo.class);
        try {
            orders=jdbcTemplate.query(sql, rowMapper,id);
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return orders;

    }

    //조리중
    public int cook(int id){
        String sql ="UPDATE orderinformation SET order_approval_status = 1 WHERE order_id = ?";
        int rs=-1;
        try {
            return jdbcTemplate.update(sql,id);
        } catch (Exception e) {
            // 예외 처리 로직 (예: 로깅)
            e.printStackTrace();
            return -1;
        }
    }
    //라이더 배정
    //라이더 배정은 1 부여 주문 거절은 3부여ㅓ
    public int rider(int id){
        String sql ="UPDATE orderinformation SET order_approval_status = 2 WHERE order_id = ?";
        int rs=-1;
        try {
            return jdbcTemplate.update(sql,id);
        } catch (Exception e) {
            // 예외 처리 로직 (예: 로깅)
            e.printStackTrace();
            return -1;
        }
    }

    //주문거절
    public int refuse(int id){
        String sql ="UPDATE orderinformation SET order_approval_status = 5 WHERE order_id = ?";
        int rs=-1;
        try {
            return jdbcTemplate.update(sql,id);
        } catch (Exception e) {
            // 예외 처리 로직 (예: 로깅)
            e.printStackTrace();
            return -1;
        }
    }
    //결제 내역 불러오기
    public List<StoreOrderInformationVo> orderReceipt(int store_id){
        String sql = "SELECT o.order_id, o.customer_id, o.store_id, o.order_details, o.total_price, o.user_x, o.user_y, " +
                "u.Email AS email, u.Name AS name " +
                "FROM OrderInformation o " +
                "JOIN UserInformation u ON o.customer_id = u.user_id " +
                "WHERE o.store_id = ? AND order_approval_status = 4";
        List<StoreOrderInformationVo> order_info = new ArrayList<StoreOrderInformationVo>();
        RowMapper<StoreOrderInformationVo> rowMapper= BeanPropertyRowMapper.newInstance(StoreOrderInformationVo.class);
        try {
            order_info = jdbcTemplate.query(sql, rowMapper, store_id);
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return order_info;
    }

    //매출 내역 조회 (현재 매출 내역을 확인할 표에서 수행될 기능)
    public List<StoreOrderInformationVo> orderSales_info(int store_id){
        String sql = "SELECT store_id, order_details, total_price, order_date " +
                "FROM OrderInformation " +
                "WHERE store_id = ? AND order_approval_status = 4";

        List<StoreOrderInformationVo> orderSales = new ArrayList<StoreOrderInformationVo>();
        RowMapper<StoreOrderInformationVo> rowMapper= BeanPropertyRowMapper.newInstance(StoreOrderInformationVo.class);
        try {
            orderSales = jdbcTemplate.query(sql, rowMapper,store_id);
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return orderSales;
    }

    //업체 정보 불러오기
    public StoreRegistrationVo store_info(int id) {
        String sql = "SELECT * FROM StoreRegistration WHERE owner_id = ? ;";

            return jdbcTemplate.queryForObject(sql, new Object[]{id},new BeanPropertyRowMapper<>(StoreRegistrationVo.class));

    }

    //이미지가 존재하는 업체 내용 수정
    public int store_edit_img(StoreRegistrationVo storeRegistrationVo){
        String sql = "UPDATE StoreRegistration SET store_name = ? , store_address = ? ,store_description = ? ,store_image=?,store_x=?,store_y=? WHERE owner_id = ? ";

        int rs=-1;
        try {
            return jdbcTemplate.update(sql,storeRegistrationVo.getStore_name(),storeRegistrationVo.getStore_address(),storeRegistrationVo.getStore_description(),storeRegistrationVo.getStore_image(),storeRegistrationVo.getStore_x(),storeRegistrationVo.getStore_y(),storeRegistrationVo.getOwner_id());
        } catch (Exception e) {
            // 예외 처리 로직 (예: 로깅)
            e.printStackTrace();
            return -1;
        }


    }

    public int store_edit(StoreRegistrationVo storeRegistrationVo){
        String sql = "UPDATE StoreRegistration SET store_name = ? , store_address = ? ,store_description = ? ,store_x=?,store_y=? WHERE owner_id = ? ";

        int rs=-1;
        try {
            return jdbcTemplate.update(sql,storeRegistrationVo.getStore_name(),storeRegistrationVo.getStore_address(),storeRegistrationVo.getStore_description(),storeRegistrationVo.getStore_x(),storeRegistrationVo.getStore_y(),storeRegistrationVo.getOwner_id());
        } catch (Exception e) {
            // 예외 처리 로직 (예: 로깅)
            e.printStackTrace();
            return -1;
        }


    }

    //업체삭제
    public int store_delete(int store_id){
        String sql = "delete from StoreRegistration where store_id=?;";

        int rs=-1;
        try {
            jdbcTemplate.update(sql,store_id);
            return 1;

        } catch (Exception e) {
            // 예외 처리 로직 (예: 로깅)
            e.printStackTrace();
            return -1;
        }


    }






}