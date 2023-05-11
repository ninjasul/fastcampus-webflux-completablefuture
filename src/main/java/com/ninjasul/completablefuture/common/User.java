package com.ninjasul.completablefuture.common;

import lombok.Getter;

import java.util.List;
import java.util.Optional;

@Getter
public class User {
    private final String id;
    private final String name;
    private final int age;
    private final Optional<Image> profileImage;

    private final List<Article> articleList;

    private final Long followCount;

    public User(final String id, final String name, final int age, final Optional<Image> profileImage, final List<Article> articleList, final Long followCount) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.profileImage = profileImage;
        this.articleList = articleList;
        this.followCount = followCount;
    }
}
