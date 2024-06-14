package org.example.backend.service;

import org.example.backend.store.StoreInformationVo;
import org.example.backend.store.StoreRegistrationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SearchDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    //음식점 조회 카테고리형식
    public List<StoreRegistrationVo> storeList(String num){
        String sql="select * from StoreRegistration where store_ca=? AND approval_status=1";

        List<StoreRegistrationVo> stoes = new ArrayList<StoreRegistrationVo>();
        RowMapper<StoreRegistrationVo> rowMapper = BeanPropertyRowMapper.newInstance(StoreRegistrationVo.class);
        try{
            stoes=jdbcTemplate.query(sql,rowMapper,num);


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
}
