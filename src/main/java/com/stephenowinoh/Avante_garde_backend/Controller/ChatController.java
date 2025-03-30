package com.stephenowinoh.Avante_garde_backend.Controller;

import com.stephenowinoh.Avante_garde_backend.Entity.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
        @MessageMapping("/chat.send")
        @SendTo("/topic/messages")
        public ChatMessage sendMessage(ChatMessage message) {
                return message;
        }
}