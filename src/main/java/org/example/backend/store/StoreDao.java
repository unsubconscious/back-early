package org.example.backend.store;

import org.springframework.beans.factory.annotation.Autowired;
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
    public int approveR(int id){
        String sql="select store_id from StoreRegistration where owner_id=? and approval_status=1;";
        try {
            return jdbcTemplate.queryForObject(sql,Integer.class,id);
        } catch (Exception e) {
            // 예외 처리 로직 (예: 로깅)
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

}
