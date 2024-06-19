package org.example.backend.service;

import org.example.backend.store.StoreInformationVo;
import org.example.backend.store.StoreRegistrationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SearchService {

    @Autowired
    private SearchDao searchDao;

    //음식점 조회 카테고리 형식
    public List<StoreRegistrationVo> storeList(String num, BigDecimal x ,BigDecimal y){
        return searchDao.storeList(num,x,y);
    }

    //메뉴목록 불러오기
    public List<StoreInformationVo> menuList(int id){
        return searchDao.menuList(id);
    }

    //주문하기
    public int order(OrderVo orderVo){
        return searchDao.order(orderVo);
    }

    //이메일탐색
    public  String email(int id){
        return  searchDao.email(id);
    }

    public List<OrderVo> getUserOrders(int userId) {
        return searchDao.getUserOrders(userId);
    }
}
