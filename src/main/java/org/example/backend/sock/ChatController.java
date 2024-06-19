package org.example.backend.sock;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import java.util.Map;
import java.util.Set;

@RestController
public class ChatController {
    @Autowired
    private SimpMessagingTemplate webSocket;
    private final SessionRegistry sessionRegistry;

    public ChatController(SimpMessagingTemplate webSocket, SessionRegistry sessionRegistry) {
        this.webSocket = webSocket;
        this.sessionRegistry = sessionRegistry;
    }


    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public OutputMessage send(Message message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new OutputMessage(HtmlUtils.htmlEscape(message.getContent()));
    }



    @MessageMapping("/sendTo") //이 경로로 날라오면
    @SendTo("/topic/sendTo")
    public OutputMessage sendToMessage(SimpMessageHeaderAccessor headerAccessor) throws Exception {

        Thread.sleep(1000); // simulated delay
        return new OutputMessage(HtmlUtils.htmlEscape("string"));
    }

    @MessageMapping("/Template")
    public void outputMessage(Map<String, String> payload,SimpMessageHeaderAccessor headerAccessor) {
        String sessionId = headerAccessor.getSessionId();
        String username = sessionRegistry.getUsername(sessionId);
        System.out.println(username);
        String message = payload.get("name");
        System.out.println(message);
        Set<String> sessionIds=sessionRegistry.getSessionIds(message);

        if(sessionIds.isEmpty()){

            System.out.println("존재하지 않는 사람입니다 ");
            webSocket.convertAndSend("/topic/template/" + username, "유저가없음");
        }


        webSocket.convertAndSend("/topic/template/" + message, "Template");
    }

    public static class Message {
        private String content;

        public Message() {}

        public Message(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class OutputMessage {
        private String content;

        public OutputMessage() {}

        public OutputMessage(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }



    public static class Messages {
        private String from;
        private String content;
        public Messages() {

        }


        public Messages(String from) {
            this.from = from;
        }



        public Messages(String content, String from) {
            this.content = content;
            this.from = from;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
