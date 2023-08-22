package com.example.meongnyangbook.alarm.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlarmMessage is a Querydsl query type for AlarmMessage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlarmMessage extends EntityPathBase<AlarmMessage> {

    private static final long serialVersionUID = -88236081L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAlarmMessage alarmMessage = new QAlarmMessage("alarmMessage");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath message = createString("message");

    public final com.example.meongnyangbook.chat.room.entity.QMessageRoom messageRoom;

    public final com.example.meongnyangbook.user.entity.QUser user;

    public QAlarmMessage(String variable) {
        this(AlarmMessage.class, forVariable(variable), INITS);
    }

    public QAlarmMessage(Path<? extends AlarmMessage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAlarmMessage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAlarmMessage(PathMetadata metadata, PathInits inits) {
        this(AlarmMessage.class, metadata, inits);
    }

    public QAlarmMessage(Class<? extends AlarmMessage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.messageRoom = inits.isInitialized("messageRoom") ? new com.example.meongnyangbook.chat.room.entity.QMessageRoom(forProperty("messageRoom"), inits.get("messageRoom")) : null;
        this.user = inits.isInitialized("user") ? new com.example.meongnyangbook.user.entity.QUser(forProperty("user")) : null;
    }

}

