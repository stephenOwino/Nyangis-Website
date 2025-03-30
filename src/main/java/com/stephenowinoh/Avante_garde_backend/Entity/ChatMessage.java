package com.stephenowinoh.Avante_garde_backend.Entity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {

        private String sender;
        private String content;


        public String getSender() {
                return sender;
        }

        public void setSender(String sender) {
                this.sender = sender;
        }

        public String getContent() {
                return content;
        }

        public void setContent(String content) {
                this.content = content;
        }
}
