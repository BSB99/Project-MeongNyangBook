package com.example.meongnyangbook.post.community.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommunity is a Querydsl query type for Community
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommunity extends EntityPathBase<Community> {

    private static final long serialVersionUID = 1852883676L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommunity community = new QCommunity("community");

    public final com.example.meongnyangbook.post.entity.QPost _super;

    //inherited
    public final ListPath<com.example.meongnyangbook.post.comment.entity.Comment, com.example.meongnyangbook.post.comment.entity.QComment> commentList;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    //inherited
    public final StringPath description;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt;

    //inherited
    public final StringPath title;

    // inherited
    public final com.example.meongnyangbook.user.entity.QUser user;

    public QCommunity(String variable) {
        this(Community.class, forVariable(variable), INITS);
    }

    public QCommunity(Path<? extends Community> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommunity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommunity(PathMetadata metadata, PathInits inits) {
        this(Community.class, metadata, inits);
    }

    public QCommunity(Class<? extends Community> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.example.meongnyangbook.post.entity.QPost(type, metadata, inits);
        this.commentList = _super.commentList;
        this.createdAt = _super.createdAt;
        this.description = _super.description;
        this.id = _super.id;
        this.modifiedAt = _super.modifiedAt;
        this.title = _super.title;
        this.user = _super.user;
    }

}

