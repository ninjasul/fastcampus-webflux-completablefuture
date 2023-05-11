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
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class UserFutureService {
    private final UserFutureRepository userRepository;
    private final ArticleFutureRepository articleRepository;
    private final ImageFutureRepository imageRepository;
    private final FollowFutureRepository followRepository;

    @SneakyThrows
    public CompletableFuture<Optional<User>> getUserById(String id) {
        return userRepository.findById(id)
            .thenComposeAsync(this::getUser);
    }

    @SneakyThrows
    private CompletableFuture<Optional<User>> getUser(Optional<UserEntity> userEntityOptional) {
        if (userEntityOptional.isEmpty()) {
            return CompletableFuture.completedFuture(Optional.empty());
        }
        var userEntity = userEntityOptional.get();

        var imageFuture = imageRepository.findById(userEntity.getProfileImageId())
            .thenApplyAsync(imageEntityOptional ->
                imageEntityOptional.map(
                    imageEntity ->
                        new Image(
                            imageEntity.getId(),
                            imageEntity.getName(),
                            imageEntity.getUrl()
                        )
                )
            );

        var articlesFuture = articleRepository.findAllByUserId(userEntity.getId())
            .thenApplyAsync(articleEntities ->
                articleEntities.stream()
                    .map(articleEntity ->
                        new Article(
                            articleEntity.getId(),
                            articleEntity.getTitle(),
                            articleEntity.getContent(),
                            articleEntity.getUserId()
                        )
                    )
                    .collect(Collectors.toList())
            );

        var followCountFuture = followRepository.countByUserId(userEntity.getId());

        return CompletableFuture.allOf(imageFuture, articlesFuture, followCountFuture)
            .thenAcceptAsync(v -> {
                log.info("Three futures are completed.");
            })
            .thenRunAsync(() -> {
                log.info("Three futures are also completed.");
            })
            .thenApplyAsync(v -> {
                try {
                    var image = imageFuture.get();
                    var articles = articlesFuture.get();
                    var followCount = followCountFuture.get();

                    return Optional.of(
                        new User(
                            userEntity.getId(),
                            userEntity.getName(),
                            userEntity.getAge(),
                            image,
                            articles,
                            followCount
                        )
                    );
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
    }
}
