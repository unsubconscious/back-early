package org.example.backend.service.search;

import org.example.backend.service.OrderListVo;
import org.example.backend.service.OrderVo;
import org.example.backend.store.StoreInformationVo;
import org.example.backend.store.StoreRegistrationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    //음식점 목록 조회(카테고리) 내위치 기반으로 조회하자
    @GetMapping("/CaList")
    public List<StoreRegistrationVo> storeList(@RequestParam("canum") String num, @RequestParam("x") BigDecimal x ,@RequestParam("y") BigDecimal y){
        return searchService.storeList(num,x,y);


    }

    //메뉴정보 불러오기(음식점 상세페이지에서) 상점 아이디 받아올꺼임
    @GetMapping("/menuList")
    public List<StoreInformationVo> menuList(@RequestParam("id") int id){
        return searchService.menuList(id);
    }

    //주문하기
    //고객아이디,상점아이디/주문내역/총가격 이렇게 값이 넘어와야한다.
    @PostMapping("/order")
    public int order(@RequestBody OrderVo orderVo) {


        return searchService.order(orderVo);
    }

    //이메일탐색 음식점 상세 정보 페이지
    //웹소켓을 위해 음식점 주인의 이메일 을 탐색한다.
    @GetMapping("/email_shop")
    public String email(@RequestParam("id") int id){
            searchService.email(id);

            return  searchService.email(id);
    }

    // 사용자 주문 정보 가져오기
    @GetMapping("/details")
    public ResponseEntity<List<OrderListVo>> getUserOrders(@RequestParam("userId") int userId) {
        System.out.println(userId);
        List<OrderListVo> orders = searchService.getUserOrders(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    //검색창에서 조회하기
    @GetMapping("/searchList")
    public List<StoreRegistrationVo> storeList2( @RequestParam("x") BigDecimal x ,@RequestParam("y") BigDecimal y,@RequestParam("searchTerm") String word) {
        System.out.println("검색조회 실행됨");
        return searchService.storeList2(x, y,word);


    }

    }
