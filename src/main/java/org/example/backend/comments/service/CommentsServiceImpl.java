package org.example.backend.comments.service;

import lombok.extern.slf4j.Slf4j;
import org.example.backend.comments.dto.CommentsVo;
import org.example.backend.comments.mapper.CommentsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class CommentsServiceImpl implements CommentsService {

    //댓글 메퍼가 필요하기 때문에 의존성 자동주입
    @Autowired
    private CommentsMapper commentsMapper;

    //추상메소드 구현 하기

    //댓글 목록 전체 조회
    @Override
    public List<CommentsVo> list(int store_id) throws Exception {
        List<CommentsVo> commentsList = commentsMapper.list(store_id);
        log.info("CommentsList : " + commentsList);
        return commentsList;
    }

    //댓글 조회(단건)
    @Override
    public CommentsVo selectById(int comment_id) throws Exception {
        CommentsVo commentsVo = commentsMapper.selectById(comment_id);
        log.info("select로 댓글 (단건) 조회 -> commentsVo : " + commentsVo);
        return commentsVo;
    }

    //댓글 등록하기
    @Transactional
    @Override
    public int insert(CommentsVo commentsVo, int id) throws Exception {
        //댓글 테이블에 사입
        int result = commentsMapper.insert(commentsVo);
        //주문 테이블 상태 6으로 바꿈 
        int rs=commentsMapper.orderup(id);
        // 강제로 예외 발생시키기
//        if (true) {
//            throw new RuntimeException("강제로 발생시킨 예외로 인해 트랜잭션 롤백됩니다.");
//        }
        return result;
    }

    //댓글 수정
    @Override
    public int update(CommentsVo commentsVo) throws Exception {
        int result = commentsMapper.update(commentsVo);
        System.out.println("댓글수정 update 확인하기 : " + result);
        return result;
    }

    //댓글 삭제시 가시성 상태 변경 : "존재하지 않는 댓글입니다"를 표시해줄놈
    @Override
    public int updateCommentVisibility(int comment_id) throws Exception {
        int result = commentsMapper.updateCommentVisibility(comment_id);
        return result;
    }

    //대댓글 등록
    @Override
    public int insertReply(CommentsVo commentsVo) throws Exception {
        int result = commentsMapper.insertReply(commentsVo);

        return result;
    }

    //대댓글 수정
    @Override
    public int updateReply(CommentsVo commentsVo) throws Exception {
        int result = commentsMapper.updateReply(commentsVo);
        log.info("대댓글 수정 확인 : " + result);
        return result;
    }

    //댓글 삭제시 가시성 상태 변경 : "존재하지 않는 댓글입니다"를 표시해줄놈
    @Override
    public int updateReplyVisibility(int comment_id) throws Exception {
        int result = commentsMapper.updateReplyVisibility(comment_id);
        log.info("대댓글 가시성 상태 변경 확인 : " + result);
        return result;
    }

}
