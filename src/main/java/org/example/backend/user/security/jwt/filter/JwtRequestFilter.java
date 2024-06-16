package org.example.backend.user.security.jwt.filter;

import org.example.backend.user.security.jwt.constants.JwtConstants;
import org.example.backend.user.security.jwt.provider.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//Jwt 토큰이 넘어왔을때의 대한 필터를 걸어줄 클래스

/* OncePerRequestFilter : 스프링 시큐리티에 관한 필터가 아닌 스프링 프레임워크에 대한 필터를 상속받아 구현 해야 한다.
   해당 메소드가 하는 역할은 매번 들어오는 요청마다 JwtRequestFilter가 걸리도록 지정해주는것.
 */
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    //해당 클래스에서도 토큰 해석이 필요해서 jwtTokenProvider를 멤버변수로 선언한다.
    private final JwtTokenProvider jwtTokenProvider;

    // JwtTokenProvider 생성자에서 받아오기
    public JwtRequestFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /* JWT 요청 필터
        - 클라이언트에서 요청을 보내면 request > headers > Authrization (JWT) 토큰을 가져오기
        - JWT 토큰 유효성 검사
     */
    //필터가 동작할때 처리하는 로직 (해당 로직에서 요청 객체와 응답 객체를 처리 해준다)
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 헤더에서 JWT 토큰을 가져온다.
        String header = request.getHeader(JwtConstants.TOKEN_HEADER);
        log.info("authorization : " + header);

        // jwt 토큰이 없으면 다음 필터로 이동하는 로직
        // Bearer + {jwt} 체크
        if(header == null || header.length() == 0 || !header.startsWith(JwtConstants.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        // jwt 토큰 해석
        // Bearer + {jwt} -> "Bearer " 제거
        String jwt = header.replace(JwtConstants.TOKEN_PREFIX, "");

        // 토큰 해석
        Authentication authentication = jwtTokenProvider.getAuthentication(jwt);

        // 토큰 유효성 검사
        if( jwtTokenProvider.validateToken(jwt)) {
            log.info("유효한 JWT 토큰 입니다");

            // 유효하면 로그인 처리 (비밀번호를 모르기 때문에 시큐리티로 로그인 처리를 해줌)
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 다음 필터
        filterChain.doFilter(request, response);
    }
}
