package com.ninjasul.completablefuture.future.repository;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Slf4j
public class FollowFutureRepository {
    private final Map<String, Long> userFollowCountMap;

    public FollowFutureRepository() {
        userFollowCountMap = Map.of("1234", 1000L);
    }

    @SneakyThrows
    public CompletableFuture<Long> countByUserId(String userId) {
        log.info("FollowRepository.countByUserId: {}", userId);
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return userFollowCountMap.getOrDefault(userId, 0L);
        });
    }
}
