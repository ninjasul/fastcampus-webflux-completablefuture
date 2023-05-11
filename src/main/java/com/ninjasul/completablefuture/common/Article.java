package com.ninjasul.completablefuture.common;

import lombok.Getter;

@Getter
public class Article {
    private final String id;
    private final String title;
    private final String content;
    private final String userId;

    public Article(final String id, final String title, final String content, final String userId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userId = userId;
    }
}
