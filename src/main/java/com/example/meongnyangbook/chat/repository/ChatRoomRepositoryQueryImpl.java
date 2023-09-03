package com.example.meongnyangbook.chat.repository;

import com.example.meongnyangbook.chat.entity.ChatRoom;
import com.example.meongnyangbook.chat.entity.QChatRoom;
import com.example.meongnyangbook.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ChatRoomRepositoryQueryImpl implements ChatRoomRepositoryQuery {
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public ChatRoom findByConstructorAndParticipant(User constructor, User participant) {
        return jpaQueryFactory
                .selectFrom(QChatRoom.chatRoom)
                .where(QChatRoom.chatRoom.hostUserId.eq(constructor)
                        .and(QChatRoom.chatRoom.guestUserId.eq(participant))
                        .or(QChatRoom.chatRoom.hostUserId.eq(participant)
                                .and(QChatRoom.chatRoom.guestUserId.eq(constructor))))
                .fetchOne();
    }

    @Override
    public List<ChatRoom> findByUserChatRoom(User currentUser) {
        return jpaQueryFactory
                .selectFrom(QChatRoom.chatRoom)
                .where(QChatRoom.chatRoom.hostUserId.eq(currentUser)
                        .or(QChatRoom.chatRoom.guestUserId.eq(currentUser)))
                .fetch();
    }
}
