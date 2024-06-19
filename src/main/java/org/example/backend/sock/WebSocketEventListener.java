package org.example.backend.sock;

import org.example.backend.user.security.jwt.provider.JwtTokenProvider;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.List;


@Component
public class WebSocketEventListener {

    private final SessionRegistry sessionRegistry;

    private final JwtTokenProvider jwtTokenProvider;

    public WebSocketEventListener(SessionRegistry sessionRegistry,JwtTokenProvider jwtTokenProvider) {
        this.sessionRegistry = sessionRegistry;
        this.jwtTokenProvider=jwtTokenProvider;
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        System.out.println("실행");
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        List<String> authorizationHeaders = accessor.getNativeHeader("Authorization");
        System.out.println(authorizationHeaders);
        if (authorizationHeaders != null && !authorizationHeaders.isEmpty()) {
//            String username = authorizationHeaders.get(0).substring(7);
            Authentication authentication = jwtTokenProvider.getAuthentication(authorizationHeaders.get(0));
            String username = authentication.getName();
            System.out.println("이름"+username);
            // Remove "Bearer " prefix
            sessionRegistry.registerSession(sessionId, username);
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        sessionRegistry.unregisterSession(sessionId);
    }
}
