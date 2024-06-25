package org.example.backend.rider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/rider")
public class RiderController {
    @Autowired
    private RiderService riderService;

    //배달전체 목록 불러오기
    @GetMapping("/order")
    public List<RiderVo> orderlist(@RequestParam("x")BigDecimal x,@RequestParam("y")BigDecimal y){

        return riderService.orderlist(x,y);


    }
    //배달 콜 받기
    @PostMapping("/call")
    public int call(@RequestBody RiderVo riderVo){


        return riderService.call(riderVo);
    }

    //콜 수락 목록 조회 하기
    @GetMapping("/orderCall")
    public List<RiderVo> orderCall(@RequestParam("id") int id){

        return riderService.orderCall(id);


    }

    //배달 완료 하기
    @PostMapping("/order/finish")
    public int finish(@RequestBody RiderVo riderVo){


        return riderService.finish(riderVo);
    }


}
