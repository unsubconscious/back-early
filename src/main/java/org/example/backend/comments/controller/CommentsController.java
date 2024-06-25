package org.example.backend.comments.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.backend.comments.dto.CommentsVo;
import org.example.backend.comments.service.CommentsService;
import org.example.backend.rider.RiderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/* 댓글 목록 : [GET] /comments/{comments_id}
   댓글 등록 : [POST] /reply
   댓글 수정 : [PUT]  /reply
   댓글 삭제 : [DELETE] /reply
 */

@Slf4j
@RestController
@RequestMapping("/comments") //엔드포인트 생성
public class CommentsController {

    //서비스를 먼저 등록(자동주입) 한다
    @Autowired
    private CommentsService commentsService;

    /** 댓글 목록 조회
        @return
        @throws Exception
     */


    @GetMapping("/list")
    public List<CommentsVo> list(@RequestParam("store_id") int store_id) throws Exception {
        return commentsService.list(store_id);
    }

     /** 댓글 목록 조회
        @param commentsVo
        @return
        @throws Exception
     */

     //댓글 등록
    @PostMapping("")
    public ResponseEntity<String> insert(@RequestBody CommentsVo commentsVo) throws Exception {
//        System.out.println(commentsVo.getAuthor_name());
//        System.out.println(commentsVo.getAuthor_id());
        // 데이터 요청
        int result = commentsService.insert(commentsVo);
        if(result > 0) {
            //데이터 처리 성공
            return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED); //CREATED : 201번 상태코드가 들어있음
        }
        return new ResponseEntity<>("FAIL", HttpStatus.OK); //OK : 200번 상태코드가 들어있음
    }
}
