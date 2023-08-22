package com.example.meongnyangbook.alarm.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlarmComment is a Querydsl query type for AlarmComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlarmComment extends EntityPathBase<AlarmComment> {

    private static final long serialVersionUID = -92762585L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAlarmComment alarmComment = new QAlarmComment("alarmComment");

    public final StringPath commentContent = createString("commentContent");

    public final StringPath commentUser = createString("commentUser");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath postTitle = createString("postTitle");

    public final com.example.meongnyangbook.user.entity.QUser user;

    public QAlarmComment(String variable) {
        this(AlarmComment.class, forVariable(variable), INITS);
    }

    public QAlarmComment(Path<? extends AlarmComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAlarmComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAlarmComment(PathMetadata metadata, PathInits inits) {
        this(AlarmComment.class, metadata, inits);
    }

    public QAlarmComment(Class<? extends AlarmComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.example.meongnyangbook.user.entity.QUser(forProperty("user")) : null;
    }

}

