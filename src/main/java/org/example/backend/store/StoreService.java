package org.example.backend.store;

import org.example.backend.comments.dto.CommentsVo;
import org.example.backend.store.dto.ReportsVo;
import org.example.backend.store.dto.StoreInformationVo;
import org.example.backend.store.dto.StoreOrderInformationVo;
import org.example.backend.store.dto.StoreRegistrationVo;
import org.example.backend.store.except.StoreNotFoundException;
import org.example.backend.store.except.StoreServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;

@Service
public class StoreService {

    @Autowired
    private StoreDao storeDao;

    //상점등록 서비스
    public  int storeInsert(StoreRegistrationVo storeRegistrationVo){
        return storeDao.storeInsert(storeRegistrationVo);
    }

    //승인 확인 서비스
    public int count(int id){
        return storeDao.approveR(id);
    }

    //메뉴 등록 서비스
    public int menuRs(StoreInformationVo storeInformationVo){
        return storeDao.menuRs(storeInformationVo);
    }

    //메뉴 목록 조회 서비스
    public List<StoreInformationVo> menuList(int id){
        return storeDao.menuList(id);
    }

    //메뉴 수정 서비스
    public int menuedit(StoreInformationVo storeInformationVo){

        if (storeInformationVo.getMenuImage()==null){
            // 이미지가 null  일경우에는 원래값 그래도 사용
            return storeDao.menuedit2(storeInformationVo);
        }
        else{
            return storeDao.menuedit(storeInformationVo);
        }

    }

    //메뉴 삭제
    public int menudel(int id, String name){
           return storeDao.menudel(id,name);
    }

    //주문알람
    public List<StoreOrderInformationVo> order(int id){
        return storeDao.order(id);
    }
    //조리중
    public int cook(int id){
        return storeDao.cook(id);
    }

    //라이더배정
    public int rider(int id){
        return storeDao.rider(id);
    }

    //주문거절
    public int refuse(int id){
        return storeDao.refuse(id);
    }

    //업체 주문 내역 조회
    public List<StoreOrderInformationVo> orderReceipt(int store_id){
        return storeDao.orderReceipt(store_id);
    }

    //매출 내역 조회
    public List<StoreOrderInformationVo> orderSales_info(int store_id){
        return storeDao.orderSales_info(store_id);
    }

    //업체 수정전 내용 받아오기
    public StoreRegistrationVo store_info(int id) {
        //GlobalExceptionHandler 오류 발생시 여기로 넘어가서 내가 원하는 오류값을 나타낼수 있음
        try {
            return storeDao.store_info(id);
        } catch (EmptyResultDataAccessException e) {
            //내가 지정한 오류다 이름이다.
            throw new StoreNotFoundException("Store not found for owner ID: " + id);
        } catch (Exception e) {
            throw new StoreServiceException("An error occurred while fetching store information", e);
        }
    }

    //업체내용 수정하기
    public int store_edit(StoreRegistrationVo storeRegistrationVo){

        if(storeRegistrationVo.getStore_image()!=null){
            //이미지가 존재할경우
            return storeDao.store_edit_img(storeRegistrationVo);

        }else{
            //이미지가 존재하지 않을 경우
            return storeDao.store_edit(storeRegistrationVo);
        }

    }

    //업체 삭제 승인
    public int store_delete(int id){
        return storeDao.store_delete(id);

    }

    //댓글 목록불러오기
    public List<CommentsVo> commentList(int id){
        return  storeDao.commentList(id);
    }

    //댓글신고하기
    @Transactional
    public int report(ReportsVo reportsVo){
        //댓글신고테이블에 기입
        int data1=storeDao.report(reportsVo);
        //댓글 테이블 상태 수정 2로
        int data2=storeDao.reportOrder(reportsVo.getCommentId());


      return 1;


    }
    //업체가 존재하는 확인
    public int exist(int id){

        return storeDao.exist(id);

    }


}
