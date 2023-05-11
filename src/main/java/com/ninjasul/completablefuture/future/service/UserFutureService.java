package com.ninjasul.completablefuture.future.service;

import com.ninjasul.completablefuture.common.Article;
import com.ninjasul.completablefuture.common.Image;
import com.ninjasul.completablefuture.common.User;
import com.ninjasul.completablefuture.common.entity.UserEntity;
import com.ninjasul.completablefuture.future.repository.ArticleFutureRepository;
import com.ninjasul.completablefuture.future.repository.FollowFutureRepository;
import com.ninjasul.completablefuture.future.repository.ImageFutureRepository;
import com.ninjasul.completablefuture.future.repository.UserFutureRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserFutureService {
    private final UserFutureRepository userRepository;
    private final ArticleFutureRepository articleRepository;
    private final ImageFutureRepository imageRepository;
    private final FollowFutureRepository followRepository;

    @SneakyThrows
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id).get()
            .map(this::getUser);
    }

    @SneakyThrows
    private User getUser(UserEntity user) {
        var image = imageRepository.findById(user.getProfileImageId()).get()
            .map(imageEntity -> new Image(imageEntity.getId(), imageEntity.getName(), imageEntity.getUrl()));

        var articles = articleRepository.findAllByUserId(user.getId()).get()
            .stream()
            .map(articleEntity ->
                new Article(
                    articleEntity.getId(),
                    articleEntity.getTitle(),
                    articleEntity.getContent(),
                    articleEntity.getUserId()
                )
            )
            .collect(Collectors.toList());

        var followCount = followRepository.countByUserId(user.getId()).get();

        return new User(
            user.getId(),
            user.getName(),
            user.getAge(),
            image,
            articles,
            followCount
        );
    }
}
