package com.example.meongnyangbook.post.adoption.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAdoption is a Querydsl query type for Adoption
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAdoption extends EntityPathBase<Adoption> {

    private static final long serialVersionUID = -276336954L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAdoption adoption = new QAdoption("adoption");

    public final com.example.meongnyangbook.post.entity.QPost _super;

    public final NumberPath<Integer> animalAge = createNumber("animalAge", Integer.class);

    public final EnumPath<com.example.meongnyangbook.post.entity.AnimalGenderEnum> animalGender = createEnum("animalGender", com.example.meongnyangbook.post.entity.AnimalGenderEnum.class);

    public final StringPath animalName = createString("animalName");

    public final EnumPath<com.example.meongnyangbook.post.entity.AreaEnum> area = createEnum("area", com.example.meongnyangbook.post.entity.AreaEnum.class);

    public final EnumPath<com.example.meongnyangbook.post.entity.CategoryEnum> category = createEnum("category", com.example.meongnyangbook.post.entity.CategoryEnum.class);

    //inherited
    public final ListPath<com.example.meongnyangbook.post.comment.entity.Comment, com.example.meongnyangbook.post.comment.entity.QComment> commentList;

    public final BooleanPath completion = createBoolean("completion");

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

    public QAdoption(String variable) {
        this(Adoption.class, forVariable(variable), INITS);
    }

    public QAdoption(Path<? extends Adoption> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAdoption(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAdoption(PathMetadata metadata, PathInits inits) {
        this(Adoption.class, metadata, inits);
    }

    public QAdoption(Class<? extends Adoption> type, PathMetadata metadata, PathInits inits) {
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

