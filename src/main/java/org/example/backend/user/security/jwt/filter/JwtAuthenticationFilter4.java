package org.example.backend.user.security.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.user.dto.CustomUser;
import org.example.backend.user.security.jwt.constants.JwtConstants;
import org.example.backend.user.security.jwt.provider.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/* 해당 로직에 대한 전체적인 흐름
            (/login)
   client -> filter -> server
   1. 넘겨 받은 username(id), password가 일치하는지 인증 시도 (인증을 시도하는 메소드 : attemptAuthentication)
      - 인증 실패시 : response의 > status(상태코드 객체) : 즉 response(응답)을 받으면 status(상태코드 객체)에 401을 담아 응답한다.
      (인증 x) (UNAUTHORIZED)

   2. 인증 성공 (successfulAuthentication)
    -> JWT 토큰을 생성
    -> response > headers > authorization : (JWT)

 */

// JwtAuthenticationFilter는 스프링 시큐리티와 연결을 위해 UsernamePasswordAuthenticationFilter를 상속받아서 구현 해야 한다.
@Slf4j
public class JwtAuthenticationFilter4 extends UsernamePasswordAuthenticationFilter {

    //해당 방식으로 필터에서 의존성 주입이 불가능 하다고 한다. 그래서 아래에 생성자를 만들어 사용하는 방법으로 처리한다.
    //@Autowired
    //private AuthenticationManager authenticationManager;

    //아래 생성자를 통해 authenticationManager, jwtTokenProvider를 가져오려고 작성한 코드
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    // authenticationManager, jwtTokenProvider를 받아올 생성자
    public JwtAuthenticationFilter4(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;

        // 필터 URL 경로 설정 : /login
        setFilterProcessesUrl(JwtConstants.AUTH_LOGIN_URL4); // 실질적인 /login 경로를 정의
    }

    /* attemptAuthentication : 인증을 시도하는 필터 메소드
       /login 경로로 요청하면, 필터로 걸러 인증을 시도한다.
    */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        System.out.println("로그인인가요?:");
        //request 객체를 이용해 파라미터를 꺼내올 수 있다.
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        log.info("email : " + email);
        log.info("password : " + password);

        //사용자 인증정보 객체 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);

        System.out.println("로그인2");

        //사용자 인증 (로그인)
        authentication = authenticationManager.authenticate(authentication);
        //authenticate 메서드가 실행되면 UserDetailsService, PasswordEncoder, Bcrypt 설정이 각각 타게 된다.

        log.info("인증 여부 : " + authentication.isAuthenticated());
        System.out.println("로그인3");

        //인증 실패(email (유저의 실제 id), password 불일치)
        if(!authentication.isAuthenticated()) {
            log.info("인증 실패 : ID or PW가 일치하지 않습니다!");
            response.setStatus(401); // UNAUTHORIZED (인증 실패)
        }
        return authentication;
    }

    /*  successfulAuthentication : 인증이 성공하였을때 실행될 메소드
        - JWT 토큰을 생성
        - JWT 토큰을 응답 헤더에 설정 (담아주기)
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        //토큰 발급을 하는 로직 작성
        log.info("인증 성공!");

        //authentication : 인증된 정보
        //getPrincipal : 인증된 사용자의 정보
        CustomUser user = (CustomUser) authentication.getPrincipal(); //인증된 사용자의 정보를 호출 한다.
        int userNo = user.getUser().getUser_id();
        String userId = user.getUser().getEmail();

        //권한을 확인할 수 있는 로직
        List<String> roles = user.getUser().getAuthList().stream()
                                                        .map((auth) -> auth.getAuth())
                                                        .collect(Collectors.toList());
        System.out.println("권한 확인");
        System.out.println(roles);
        // JWT 토큰 생성 요청
        String jwt = jwtTokenProvider.createToken(userNo, userId, roles);
        // 특정 권한을 체크
        boolean hasRequiredRole = roles.contains("ROLE_RIDER");

        if (!hasRequiredRole) {
            jwt="권한존재하지 않음";
            log.info("인증 실패 : 권한이 없습니다!");
            response.addHeader(JwtConstants.TOKEN_HEADER, JwtConstants.TOKEN_PREFIX + jwt);
            response.setStatus(207); // FORBIDDEN (권한 없음)

            return;
        }

        // 응답 헤더 설정 : { Authorization : Bearer + {jwt} }
        response.addHeader(JwtConstants.TOKEN_HEADER, JwtConstants.TOKEN_PREFIX + jwt);
        response.setStatus(200);
    }
}
