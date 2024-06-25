package org.example.backend.comments.service;

import org.example.backend.comments.dto.CommentsVo;

import java.util.List;

public interface CommentsService {

    //댓글 목록
    public List<CommentsVo> list(int store_id) throws Exception;

    //댓글 조회
    //public CommentsVo select(int store_id) throws Exception;

    //댓글 등록
    public int insert(CommentsVo commentsVo) throws Exception;

    //댓글 수정
    public int update(CommentsVo commentsVo) throws Exception;

    //댓글 삭제
    public int delete(int comment_id) throws Exception;
}
