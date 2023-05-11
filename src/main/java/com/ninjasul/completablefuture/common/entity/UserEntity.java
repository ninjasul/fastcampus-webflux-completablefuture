package com.ninjasul.completablefuture.common.entity;

import lombok.Getter;

@Getter
public class UserEntity {
    private final String id;
    private final String name;
    private final int age;
    private final String profileImageId;

    public UserEntity(final String id, final String name, final int age, final String profileImageId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.profileImageId = profileImageId;
    }
}
