package com.example.meongnyangbook.post.attachment.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAttachmentUrl is a Querydsl query type for AttachmentUrl
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAttachmentUrl extends EntityPathBase<AttachmentUrl> {

    private static final long serialVersionUID = -739940877L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAttachmentUrl attachmentUrl = new QAttachmentUrl("attachmentUrl");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.example.meongnyangbook.post.entity.QPost post;

    public final StringPath url = createString("url");

    public QAttachmentUrl(String variable) {
        this(AttachmentUrl.class, forVariable(variable), INITS);
    }

    public QAttachmentUrl(Path<? extends AttachmentUrl> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAttachmentUrl(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAttachmentUrl(PathMetadata metadata, PathInits inits) {
        this(AttachmentUrl.class, metadata, inits);
    }

    public QAttachmentUrl(Class<? extends AttachmentUrl> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new com.example.meongnyangbook.post.entity.QPost(forProperty("post"), inits.get("post")) : null;
    }

}

