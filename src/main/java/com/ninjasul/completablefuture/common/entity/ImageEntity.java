package com.ninjasul.completablefuture.common.entity;

import lombok.Getter;

@Getter
public class ImageEntity {
    private final String id;
    private final String name;
    private final String url;

    public ImageEntity(final String id, final String name, final String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }
}
