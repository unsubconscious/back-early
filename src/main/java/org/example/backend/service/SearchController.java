package org.example.backend.service;

import lombok.Getter;
import org.example.backend.store.StoreInformationVo;
import org.example.backend.store.StoreRegistrationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    //음식점 목록 조회(카테고리)
    @GetMapping("/CaList")
    public List<StoreRegistrationVo> storeList(@RequestParam("canum") String num){
        return searchService.storeList(num);


    }

    //메뉴정보 불러오기(음식점 상세페이지에서) 상점 아이디 받아올꺼임
    @GetMapping("/menuList")
    public List<StoreInformationVo> menuList(@RequestParam("") int id){
        return searchService.menuList(id);
    }




}
