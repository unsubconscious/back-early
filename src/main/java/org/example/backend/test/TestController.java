package org.example.backend.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.store.dto.StoreOrderInformationVo;
import org.example.backend.user.dto.CustomUser;
import org.example.backend.user.dto.User;
import org.example.backend.user.security.jwt.provider.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
//@RequiredArgsConstructor
public class TestController {

//    @Autowired
//    TestService testService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @GetMapping("/api/userinfo")
    public User getUserInfo(@RequestHeader("Authorization") String authHeader) {
        log.info(":::: jwt토큰으로부터 값 꺼내오기 ::::");
        Authentication authentication = jwtTokenProvider.getAuthentication(authHeader);

        if (authentication == null) {
            throw new RuntimeException("Invalid JWT token");
        }

        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        return customUser.getUser();
    }

    //ai 테스트 하기 위한 정보 불러오기 api
    @GetMapping("/id")
    public String menu (@RequestParam("id") int id) throws JsonProcessingException {

        String sql = "SELECT order_details \n" +
                "FROM orderinformation \n" +
                "WHERE customer_id = ? \n" +
                "ORDER BY order_date DESC \n" +
                "LIMIT 3;";
        List<String> order_info = new ArrayList<>();

        try {
            order_info = jdbcTemplate.query(sql, new Object[]{id}, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString("order_details");
                }
            });
            }catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        String names=" ";
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> menuNames = new ArrayList<>();
        for (String orderDetail : order_info) {
            //제인스 데이터로 받아온 거 변환.
            List<Map<String, Object>> orderList = objectMapper.readValue(orderDetail, new TypeReference<List<Map<String, Object>>>() {});

            for (Map<String, Object> menuItem : orderList) {
                String menuName = (String) menuItem.get("menuName");
                menuNames.add(menuName);
                names+=menuName+" ";
            }
        }

        //모든 메뉴 조회하기
        String menuName="";

        String sql2="select menu_name from storeinformation;";
        List<String> menus = new ArrayList<>();
        try {
            menus = jdbcTemplate.query(sql2, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString("menu_name");
                }
            });
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        for (String menu : menus) {
            //제인스 데이터로 받아온 거 변환.
            System.out.println(menu);
            menuName+=" "+menu;
        }





        return names;
    }

    @GetMapping("TEST")
    public void names(){
        String name1="1. 돈까스: 바삭하고 부드러운 돈까스는 감자탕과 잘 어울립니다.";
        // 끝 인덱스를 찾기 위해 ":"의 위치를 구합니다.
        int endIndex = name1.indexOf(":");

        // 시작 인덱스와 끝 인덱스 사이의 문자열을 추출합니다.
        String extracted = name1.substring(3, endIndex);
        System.out.println(extracted);

    }

//    @GetMapping("/hello")
//    public List<User> test(){
//        testService.findAllUsers();
//        return testService.findAllUsers();
//    }

//    @GetMapping("/{id}")
//    public User getUserById(@PathVariable Long id) {
//        return testService.findUserById(id);
//    }
}
