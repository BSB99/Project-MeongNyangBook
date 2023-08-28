package com.example.meongnyangbook.chat.repository;

import com.example.meongnyangbook.chat.entity.ChatRoom;
import com.example.meongnyangbook.chat.entity.QChatRoom;
import com.example.meongnyangbook.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChatRoomRepositoryQueryImpl implements ChatRoomRepositoryQuery {
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public ChatRoom findByConstructorAndParticipant(User constructor, User participant) {
        return jpaQueryFactory
                .selectFrom(QChatRoom.chatRoom)
                .where(QChatRoom.chatRoom.constructor.eq(constructor)
                        .and(QChatRoom.chatRoom.participant.eq(participant))
                        .or(QChatRoom.chatRoom.constructor.eq(participant)
                                .and(QChatRoom.chatRoom.participant.eq(constructor))))
                .fetchOne();
    }
}
