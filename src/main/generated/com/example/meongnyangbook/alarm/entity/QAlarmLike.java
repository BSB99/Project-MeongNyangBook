package com.example.meongnyangbook.alarm.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlarmLike is a Querydsl query type for AlarmLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlarmLike extends EntityPathBase<AlarmLike> {

    private static final long serialVersionUID = 369190095L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAlarmLike alarmLike = new QAlarmLike("alarmLike");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath likeUser = createString("likeUser");

    public final StringPath postTitle = createString("postTitle");

    public final com.example.meongnyangbook.user.entity.QUser user;

    public QAlarmLike(String variable) {
        this(AlarmLike.class, forVariable(variable), INITS);
    }

    public QAlarmLike(Path<? extends AlarmLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAlarmLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAlarmLike(PathMetadata metadata, PathInits inits) {
        this(AlarmLike.class, metadata, inits);
    }

    public QAlarmLike(Class<? extends AlarmLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.example.meongnyangbook.user.entity.QUser(forProperty("user")) : null;
    }

}

