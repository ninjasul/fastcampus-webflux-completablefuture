package com.ninjasul.completablefuture.common.entity;

import lombok.Getter;

@Getter
public class ArticleEntity {
    private final String id;
    private final String title;
    private final String content;
    private final String userId;

    public ArticleEntity(final String id, final String title, final String content, final String userId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userId = userId;
    }
}
