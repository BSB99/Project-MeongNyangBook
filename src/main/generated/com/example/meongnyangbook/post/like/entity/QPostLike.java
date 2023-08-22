package com.example.meongnyangbook.post.like.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostLike is a Querydsl query type for PostLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostLike extends EntityPathBase<PostLike> {

    private static final long serialVersionUID = 487445636L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostLike postLike = new QPostLike("postLike");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.example.meongnyangbook.post.entity.QPost post;

    public final com.example.meongnyangbook.user.entity.QUser user;

    public QPostLike(String variable) {
        this(PostLike.class, forVariable(variable), INITS);
    }

    public QPostLike(Path<? extends PostLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostLike(PathMetadata metadata, PathInits inits) {
        this(PostLike.class, metadata, inits);
    }

    public QPostLike(Class<? extends PostLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new com.example.meongnyangbook.post.entity.QPost(forProperty("post"), inits.get("post")) : null;
        this.user = inits.isInitialized("user") ? new com.example.meongnyangbook.user.entity.QUser(forProperty("user")) : null;
    }

}

