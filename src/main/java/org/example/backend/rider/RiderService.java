package org.example.backend.rider;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class RiderService {
    @Autowired
    private RiderDao riderDao;


    //배달전체 목록 불러오기
    public List<RiderVo> orderlist(BigDecimal x, BigDecimal y) {
        int EARTH_RADIUS_KM = 6371;
        double myx = x.doubleValue();
        double myy = y.doubleValue();
        List<RiderVo> riderVos = new ArrayList<>();

        riderVos = riderDao.orderlist(x,y);
        for (RiderVo i : riderVos) {
            double userx = i.getUser_x().doubleValue();
            double usery = i.getUser_y().doubleValue();
            double storex = i.getStore_x().doubleValue();
            double storey = i.getStore_y().doubleValue();

            // 주문자와 상점 사이의 거리 계산
            double dLat = Math.toRadians(usery - storey);
            double dLon = Math.toRadians(userx - storex);
            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                    Math.cos(Math.toRadians(storey)) * Math.cos(Math.toRadians(usery)) *
                            Math.sin(dLon / 2) * Math.sin(dLon / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            i.setDistanceToUser(Math.round((EARTH_RADIUS_KM * c)* 1000.0)/1000.0);


            // 상점과 라이더 사이의 거리 계산
            double dLat2 = Math.toRadians(storey - myy);
            double dLon2 = Math.toRadians(storex - myx);
            double a2 = Math.sin(dLat2 / 2) * Math.sin(dLat2 / 2) +
                    Math.cos(Math.toRadians(myy)) * Math.cos(Math.toRadians(storey)) *
                            Math.sin(dLon2 / 2) * Math.sin(dLon2 / 2);
            double c2 = 2 * Math.atan2(Math.sqrt(a2), Math.sqrt(1 - a2));
            i.setDistanceToStore(Math.round((EARTH_RADIUS_KM * c2)* 1000.0)/1000.0);
//
//            //배달가격 계산
            double dStore=i.getDistanceToStore();
            double dUser=i.getDistanceToUser();
            int price=0;
            if(dStore+dUser<1.5){
                price=3000;
            }
            else if ((dStore+dUser)<2){
                price=3500;
            }
            else if((dStore+dUser)<3){
                price=4000;
            }
            else{
                price=4500;
            }

            i.setDeliveryPrice(price);
        }
        return riderVos;
    }

    //배달 콜받기
    public int call(RiderVo riderVo){
        //라이더 db에 주문 내역 넣기
        int callrs=riderDao.call(riderVo);
        //주문 db 상태 수정 배달 2로 수정하기
        int order= riderDao.order(riderVo);
        if (callrs==1  && order==1)
            return 1;
        else
            return -1;

    }

    //콜수락한거 목록조회
    public List<RiderVo> orderCall(int id){

        return riderDao.orderCall(id);

    }

    //주문완료
    public int finish( RiderVo riderVo){

        //라이더 db에 상태 1로 수정
        int callrs=riderDao.finish(riderVo);
        //주문 db 상태 수정 배달 완료 4로 수정하기
        int order= riderDao.orderfinish(riderVo);
        if (callrs==1  && order==1)
            return 1;
        else
            return -1;
    }

    //라이더 완료 내역 출력
    public List<RiderVo> Receipt(int riderId){
        return riderDao.riderReceipt(riderId);
    }

    public List<RiderVo> Revenue(int riderId){
        return riderDao.riderRevenue(riderId);
    }

}
