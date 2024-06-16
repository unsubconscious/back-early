package org.example.backend.user.mapper;
import org.example.backend.user.dto.UserAuth;
import org.example.backend.user.dto.User;
import org.apache.ibatis.annotations.Mapper;

//인터페이스 생성
@Mapper //메퍼 xml 파일과 매핑 (연결) 시키기 위해 어노테이션으로 등록 해준다.
public interface UserMapper {

    //회원 등록
    public int insert(User user) throws Exception;

    //회원 조회
    public User select(int user_id) throws Exception;

    //사용자 인증 (로그인) - id
    public User login(String email);

    //회원 권한 등록
    public int insertAuth(UserAuth userAuth) throws Exception;

    //회원 수정
    public int update(User user) throws Exception;

    //회원 삭제
    public int delete(String user_id) throws Exception;
}