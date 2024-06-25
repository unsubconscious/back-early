package org.example.backend.comments.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.backend.comments.dto.CommentsVo;

import java.util.List;

@Mapper //항상 mybatis에서는 @Mapper 어노테이션을 붙여줘야 한다.
public interface CommentsMapper { //연결할 메퍼 인터페이스
    //댓글 목록
    public List<CommentsVo> list(int store_id) throws Exception;

    //댓글 조회
    public CommentsVo select(int comment_id) throws Exception;

    //댓글 등록
    public int insert(CommentsVo commentsVo) throws Exception;

    //댓글 수정
    public int update(CommentsVo commentsVo) throws Exception;

    //댓글 삭제
    public int delete(int comment_id) throws Exception;
}