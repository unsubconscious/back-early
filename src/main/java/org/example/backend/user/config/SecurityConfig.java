package org.example.backend.user.config;

import org.example.backend.user.security.jwt.filter.JwtRequestFilter;
import org.example.backend.user.security.custom.CustomUserDetailService;
import org.example.backend.user.security.jwt.filter.JwtAuthenticationFilter;
import org.example.backend.user.security.jwt.provider.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Slf4j
@Configuration
@EnableWebSecurity
// @preAuthorize, @postAuthorize, @Secured 활성화
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // 시큐리티 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("시큐리티 설정...");

        // 폼 기반 로그인 비활성화
        http.formLogin( login -> login.disable() );

        // HTTP 기본 인증 비활성화
        http.httpBasic( basic -> basic.disable() );

        // CSRF(Cross-Site Request Forgery) 공격 방어 기능 비활성화
        http.csrf( csrf -> csrf.disable() );

        // 필터 설정 ✅
        http.addFilterAt(new JwtAuthenticationFilter(authenticationManager, jwtTokenProvider), //addFilterAt() 인자에는 설정해줄 객체를 넣어주면 되고
                        UsernamePasswordAuthenticationFilter.class) // 두번째 인자에는 스프링 시큐리티에서 어떤 필터를 동작시킬껀지에 대해 사용되는 필터 클래스를 넣어준다.

                .addFilterBefore(new JwtRequestFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);

        // 인가 설정 ✅
        http.authorizeHttpRequests(authorizeRequests ->
                                    //어떤 경로에서든 접근 가능하도록 홈 경로를 지정해준다.
                                    authorizeRequests
                                            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() //정적자원의 경로 toStaticResources()를 가져와서 허용 해놓을 수도 있다.
                                            .requestMatchers("/**").permitAll()
//                                            .requestMatchers("/login").permitAll()

                                            //.requestMatchers("/user/**").hasAnyRole("USER", "ADMIN") //hasAnyRole 메서드로 유저와 어드민 권한을 지정, 여러 경로 지정시 hasAnyRole()를 사용
                                            .requestMatchers("/user/**").permitAll() //hasAnyRole 메서드로 유저와 어드민 권한을 지정, 여러 경로 지정시 hasAnyRole()를 사용
                                            .requestMatchers("/admin/**").hasRole("ADMIN") //하나의 경로만 지정할때는 hasRole로 할 수 있다.
                                            .anyRequest().authenticated() //이외의 나머지 요청들은 인증이 가능한 사용자만 요청하도록 한다는 의미
                                    );

        // 인증 방식 설정
        http.userDetailsService(customUserDetailService);

        return http.build();
    }

    // PasswordEncoder 빈 등록
    // 암호화 알고리즘 방식: Bcrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager 빈 등록
    private AuthenticationManager authenticationManager;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        System.out.println("빈");
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();

        return authenticationManager;

    }


}