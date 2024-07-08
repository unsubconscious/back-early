package org.example.backend.service.account;

import lombok.extern.slf4j.Slf4j;
import org.example.backend.service.OrderException;
import org.example.backend.service.OrderVo;
import org.example.backend.service.PayAccountVo;
import org.example.backend.service.search.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Slf4j
@Service
public class AccountService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private SearchService searchService;
    //계좌잔액확인
    public int amount(int id){
        return accountDao.amount(id);
    }

    //계좌 금액추가 하고 현재 금액 반환하기
    @Transactional
    public int deposit(int id,int price){
        //유저 계좌 번호 확인
        int accountNum=accountDao.accountId(id);
        log.info(":::: 내계좌 넘버 ::::"+accountNum);
        //현재 잔액
        if(price<=0){
            return -1;
        }else{
        accountDao.deposit(accountNum,price);
        return accountDao.amount(id);}


    }

    //결제하기
//    @Transactional
//    public int pay(int id,int price){
//        //유저 계좌 번호 확인
//        int accountNum=accountDao.accountId(id);
//        log.info(":::: 내계좌 넘버 ::::"+accountNum);
//        //현재 잔액
//        int amount=accountDao.amount(id);
//        if (amount + price<0){
//            throw new IllegalStateException("Insufficient balance");
//
//
//        }else {
//            return accountDao.withdraw(accountNum, price);
//        }
//
//
//    }

    @Transactional
    public int pay(PayAccountVo payAccountVo){
        int id=payAccountVo.getId();
        System.out.println(id);
        int price=payAccountVo.getPrice();
        System.out.println(price);
        //유저 계좌 번호 확인
        int accountNum=accountDao.accountId(id);
        log.info(":::: 내계좌 넘버 ::::"+accountNum);
        //현재 잔액
        int amount=accountDao.amount(id);
        if (amount + price<0){
            throw new IllegalStateException("Insufficient balance");


        }else {
            OrderVo orderVo =new OrderVo();
            orderVo.setCustomerId(payAccountVo.getCustomerId());
            orderVo.setStoreId(payAccountVo.getStoreId());
            orderVo.setOrderDetails(payAccountVo.getOrderDetails());
            orderVo.setTotalPrice(payAccountVo.getTotalPrice());
            orderVo.setUser_x(payAccountVo.getUser_x());
            orderVo.setUser_y(payAccountVo.getUser_y());
            int rs=searchService.order(orderVo);
            if(rs==-1){
               throw new OrderException("Order failed");
            }

            return accountDao.withdraw(accountNum, price);
        }


    }

    //계좌 등급확인하기
    public String rank( PayAccountVo payAccountVo){
        //내계좌 번호 부터 확인
        int id=payAccountVo.getId();
        System.out.println(id);
        //유저 계좌 번호 확인
        int accountNum=accountDao.accountId(id);
        String rankName=accountDao.rank(accountNum);
        return rankName;



    }


}
