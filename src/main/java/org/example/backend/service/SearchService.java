package org.example.backend.service;

import org.example.backend.store.StoreInformationVo;
import org.example.backend.store.StoreRegistrationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    private SearchDao searchDao;

    //음식점 조회 카테고리 형식
    public List<StoreRegistrationVo> storeList(String num){
        return searchDao.storeList(num);
    }

    //메뉴목록 불러오기
    public List<StoreInformationVo> menuList(int id){
        return searchDao.menuList(id);
    }
}
