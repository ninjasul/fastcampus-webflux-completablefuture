package com.ninjasul.completablefuture.future.repository;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class FollowFutureRepository {
    private final Map<String, Long> userFollowCountMap;

    public FollowFutureRepository() {
        userFollowCountMap = Map.of("1234", 1000L);
    }

    @SneakyThrows
    public Long countByUserId(String userId) {
        log.info("FollowRepository.countByUserId: {}", userId);
        Thread.sleep(1000L);
        return userFollowCountMap.getOrDefault(userId, 0L);
    }
}
