package org.example.backend.sock;

import org.example.backend.user.security.jwt.provider.JwtTokenProvider;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import jakarta.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component // 이 클래스를 Spring Bean으로 등록
public class CustomHandshakeHandler extends DefaultHandshakeHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired // jwtTokenProvider를 의존성 주입
    public CustomHandshakeHandler(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected Principal determineUser(ServerHttpRequest request,
                                      WebSocketHandler wsHandler,
                                      Map<String, Object> attributes) {

        String token = null;

        if (request instanceof ServletServerHttpRequest) {
            HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
            token = servletRequest.getParameter("token"); // Extract token from query parameter
            System.out.println("Token from query parameter: " + token);
        }

        if (token != null) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);

            if (authentication != null) {
                String username = authentication.getName(); // 이메일이 반환된다. 즉 아이디
                System.out.println("Authenticated User: " + username); // Log the username for debugging
                return new StompPrincipal(username);
            } else {
                throw new RuntimeException("Invalid JWT token");
            }
        } else {
            return new StompPrincipal(UUID.randomUUID().toString());
        }
    }
}
