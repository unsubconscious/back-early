package org.example.backend.user.security.jwt.constants;

/*  - 로그인 필터 경로
    - 토큰 헤더
    - 토큰 헤더의 접두사 (prefix)
    - 토큰 타입
 */

//JWT 필터들을 설정해주기 위해 미리 상수들을 정의 해놓은 클래스
public class JwtConstants {
    public static final String AUTH_LOGIN_URL = "/login"; //로그인 요청 경로를 지정
    public static final String AUTH_LOGIN_URL2 = "/loginStore"; //로그인 요청 경로를 지정

    public static final String AUTH_LOGIN_URL3 = "/loginAdmin"; //로그인 요청 경로를 지정
    public static final String AUTH_LOGIN_URL4 = "/loginRider"; //로그인 요청 경로를 지정

    public static final String TOKEN_HEADER = "Authorization"; //JWT의 요청 응답 헤더
    public static final String TOKEN_PREFIX = "Bearer "; //JWT의 접두사
    public static final String TOKEN_TYPE = "JWT"; //JWT의 타입은 "JWT" 상수로 지정
}
