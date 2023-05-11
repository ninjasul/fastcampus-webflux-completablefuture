package com.ninjasul.completablefuture.common;

import lombok.Getter;

@Getter
public class Image {
    private final String id;
    private final String name;
    private final String url;

    public Image(final String id, final String name, final String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }
}
