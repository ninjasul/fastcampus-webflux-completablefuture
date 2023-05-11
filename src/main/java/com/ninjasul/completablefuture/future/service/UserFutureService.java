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
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserFutureService {
    private final UserFutureRepository userRepository;
    private final ArticleFutureRepository articleRepository;
    private final ImageFutureRepository imageRepository;
    private final FollowFutureRepository followRepository;

    @SneakyThrows
    public CompletableFuture<Optional<User>> getUserById(String id) {
        return userRepository.findById(id)
            .thenCompose(this::getUser);
    }

    @SneakyThrows
    private CompletableFuture<Optional<User>> getUser(Optional<UserEntity> userEntityOptional) {
        if (userEntityOptional.isEmpty()) {
            return CompletableFuture.completedFuture(Optional.empty());
        }
        var userEntity = userEntityOptional.get();

        var image = imageRepository.findById(userEntity.getProfileImageId()).get()
            .map(imageEntity -> new Image(imageEntity.getId(), imageEntity.getName(), imageEntity.getUrl()));

        var articles = articleRepository.findAllByUserId(userEntity.getId()).get()
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

        var followCount = followRepository.countByUserId(userEntity.getId()).get();

        return CompletableFuture.completedFuture(
            Optional.of(
                new User(
                    userEntity.getId(),
                    userEntity.getName(),
                    userEntity.getAge(),
                    image,
                    articles,
                    followCount
                )
            )
        );
    }
}
