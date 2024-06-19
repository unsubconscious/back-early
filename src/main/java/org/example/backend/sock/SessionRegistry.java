package org.example.backend.sock;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class SessionRegistry {
    private final Map<String, Set<String>> userSessionMap = new ConcurrentHashMap<>();
    private final Map<String, String> sessionUserMap = new ConcurrentHashMap<>();

    public void registerSession(String sessionId, String username) {
        userSessionMap.computeIfAbsent(username, k -> new CopyOnWriteArraySet<>()).add(sessionId);
        sessionUserMap.put(sessionId, username);
    }

    public void unregisterSession(String sessionId) {
        String username = sessionUserMap.remove(sessionId);
        if (username != null) {
            Set<String> sessions = userSessionMap.get(username);
            if (sessions != null) {
                sessions.remove(sessionId);
                if (sessions.isEmpty()) {
                    userSessionMap.remove(username);
                }
            }
        }
    }

    public Set<String> getSessionIds(String username) {
        return userSessionMap.getOrDefault(username, Set.of());
    }

    public String getUsername(String sessionId) {
        return sessionUserMap.get(sessionId);
    }

}