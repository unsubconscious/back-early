package org.example.backend.comments.service;

import lombok.extern.slf4j.Slf4j;
import org.example.backend.comments.dto.CommentsVo;
import org.example.backend.comments.mapper.CommentsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CommentsServiceImpl implements CommentsService {

    //댓글 메퍼가 필요하기 때문에 의존성 자동주입
    @Autowired
    private CommentsMapper commentsMapper;

    //추상메소드 구현 하기
    @Override
    public List<CommentsVo> list(int store_id) throws Exception {
        List<CommentsVo> commentsList = commentsMapper.list(store_id);
        log.info("CommentsList : " + commentsList);
        return commentsList;
    }

    /*
    @Override
    public CommentsVo select(int store_id) throws Exception {
        CommentsVo commentsVo = commentsMapper.select(store_id);
        log.info("select -> commentsVo : " + commentsVo);
        return commentsVo;
    }
    */

    @Override
    public int insert(CommentsVo commentsVo) throws Exception {
        int result = commentsMapper.insert(commentsVo);
        return result;
    }
    @Override
    public int update(CommentsVo commentsVo) throws Exception {
        int result = commentsMapper.update(commentsVo);
        return result;
    }

    @Override
    public int delete(int comment_id) throws Exception {
        int result = commentsMapper.delete(comment_id);
        return result;
    }

}
