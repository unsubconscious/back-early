package org.example.backend.sock;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Map;
import java.util.Set;

@Controller
public class MessageController {

    private final SimpMessagingTemplate messagingTemplate;
    private final SessionRegistry sessionRegistry;

    public MessageController(SimpMessagingTemplate messagingTemplate, SessionRegistry sessionRegistry) {
        this.messagingTemplate = messagingTemplate;
        this.sessionRegistry = sessionRegistry;
    }

    @MessageMapping("/sendMessage")
    public void sendMessage(Principal principal,ChatController.Messages message, SimpMessageHeaderAccessor headerAccessor, Map<String, String> payload) {

        String messages = payload.get("content");
        String sessionId = headerAccessor.getSessionId();
        String na=payload.get("from");
        Set<String> sessionIds=sessionRegistry.getSessionIds(na);
        System.out.println("현재 이름"+principal.getName());
        System.out.println("상대방 이름"+na);
        ChatController.Messages m = new ChatController.Messages("false","false");

        if(sessionIds.isEmpty()){
            System.out.println("존재하지 않는 사람입니다");
            //주문자한테 보내는거
            messagingTemplate.convertAndSendToUser(principal.getName(),"/topic/sendMessage", m);


        }
        else{
            ChatController.Messages my = new ChatController.Messages("true","true");
            //상점 주인한테 보내는거
            messagingTemplate.convertAndSendToUser(na,"/topic/sendMessage", message);
            //주문자한테 보내는거
            messagingTemplate.convertAndSendToUser(principal.getName(),"/topic/sendMessage", my);
            messagingTemplate.convertAndSendToUser(principal.getName(),"/topic/sendMessage2", message);

            System.out.println("보내기 완료");
            System.out.println(message);
        }



    }
}

