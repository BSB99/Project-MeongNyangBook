package com.example.meongnyangbook.chat.room.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMessageRoom is a Querydsl query type for MessageRoom
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMessageRoom extends EntityPathBase<MessageRoom> {

    private static final long serialVersionUID = 348092713L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMessageRoom messageRoom = new QMessageRoom("messageRoom");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.example.meongnyangbook.user.entity.QUser receiveUser;

    public final com.example.meongnyangbook.user.entity.QUser sernderUser;

    public QMessageRoom(String variable) {
        this(MessageRoom.class, forVariable(variable), INITS);
    }

    public QMessageRoom(Path<? extends MessageRoom> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMessageRoom(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMessageRoom(PathMetadata metadata, PathInits inits) {
        this(MessageRoom.class, metadata, inits);
    }

    public QMessageRoom(Class<? extends MessageRoom> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.receiveUser = inits.isInitialized("receiveUser") ? new com.example.meongnyangbook.user.entity.QUser(forProperty("receiveUser")) : null;
        this.sernderUser = inits.isInitialized("sernderUser") ? new com.example.meongnyangbook.user.entity.QUser(forProperty("sernderUser")) : null;
    }

}

