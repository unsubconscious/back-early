package org.example.backend.comments.service;

import org.example.backend.comments.dto.CommentsVo;

import java.util.List;

public interface CommentsService {

    //댓글 목록
    public List<CommentsVo> list(int store_id) throws Exception;

    //댓글 조회(단건)
    public CommentsVo selectById(int comment_id) throws Exception;

    //댓글 등록
    public int insert(CommentsVo commentsVo, int id) throws Exception;

    //댓글 수정
    public int update(CommentsVo commentsVo) throws Exception;

    //댓글 삭제시 가시성 상태 변경 : "존재하지 않는 댓글입니다"를 표시해줄놈
    public int updateCommentVisibility(int comment_id) throws Exception;

    //대댓글 등록
    public int insertReply(CommentsVo commentsVo) throws Exception;

    //대댓글 수정
    public int updateReply(CommentsVo commentsVo) throws Exception;

    //댓글 삭제시 가시성 상태 변경 : "존재하지 않는 댓글입니다"를 표시해줄놈
    public int updateReplyVisibility(int comment_id) throws Exception;
}
