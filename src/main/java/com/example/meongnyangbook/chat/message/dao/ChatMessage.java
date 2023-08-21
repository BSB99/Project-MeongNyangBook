package com.example.meongnyangbook.chat.message.dao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    /*
    ENTER -> 채팅방 입장
    TALK -> 메시지 전송
     */

    public enum MessageType{
        ENTER, TALK
    }

    private MessageType type;
    private String roomid;
    private String sender;
    private String message;
}
