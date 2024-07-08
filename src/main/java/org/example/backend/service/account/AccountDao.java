package org.example.backend.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //계좌금액학인하기
    public int amount(int id){
        String sql="select account_amount from account where owner_id=?;";
        int rs=-1;
        try{
            rs=jdbcTemplate.queryForObject(sql,Integer.class,id);

        }catch (Exception e){
            e.printStackTrace();
        }

        return rs;



    }

    //계좌넘버 확인
    public int accountId(int id){
        String sql="select account_id from account where owner_id=?;";
        int rs=-1;
        try{
            rs=jdbcTemplate.queryForObject(sql,Integer.class,id);

        }catch (Exception e){
            e.printStackTrace();
        }

        return rs;



    }



    public int deposit(int id,int price){
        String sql="INSERT INTO accountstatus (account_id,amount,type) " +
                "VALUES (?,?,?);";
        int rs=-1;
        try{
            jdbcTemplate.update(sql,id,price,"입금");
            rs=1;

        }catch (Exception e){
            e.printStackTrace();
        }

        return rs;



    }

    public int withdraw(int id,int price){
        System.out.println("결제 실행중");
        String sql="INSERT INTO accountstatus (account_id,amount,type) " +
                "VALUES (?,?,?);";
        int rs=-1;
        try{
            jdbcTemplate.update(sql,id,price,"결제");
            System.out.println("성공");
            rs=1;

        }catch (Exception e){
            e.printStackTrace();
        }

        return rs;



    }

    //계좌 등급확인하기
    public String rank(int accountId){
        String sql="  SELECT\n" +
                "  r.Rating\n" +
                "FROM\n" +
                "  accountstatus a\n" +
                "INNER JOIN (\n" +
                "  SELECT account_id, SUM(-amount) AS total_amount\n" +
                "  FROM accountstatus\n" +
                "  WHERE type = '결제' AND account_id = ?\n" +
                "  GROUP BY account_id\n" +
                ") AS sub_a ON a.account_id = sub_a.account_id\n" +
                "JOIN rankpoint r ON sub_a.total_amount >= r.score\n" +
                "GROUP BY\n" +
                "  a.account_id, r.Rating\n" +
                "ORDER BY\n" +
                "  r.score DESC\n" +
                "LIMIT 1;";

        String rs="";
        try{
            rs=jdbcTemplate.queryForObject(sql,String.class,accountId);

        }catch (EmptyResultDataAccessException e){
            rs="Bronze";
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return rs;
    }

}
