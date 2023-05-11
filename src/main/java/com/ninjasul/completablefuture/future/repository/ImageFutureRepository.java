package com.ninjasul.completablefuture.future.repository;

import com.ninjasul.completablefuture.common.entity.ImageEntity;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Slf4j
public class ImageFutureRepository {
    private final Map<String, ImageEntity> imageMap;

    public ImageFutureRepository() {
        imageMap = Map.of(
            "image#1000", new ImageEntity("image#1000", "profileImage", "https://dailyone.com/images/1000")
        );
    }

    @SneakyThrows
    public CompletableFuture<Optional<ImageEntity>> findById(String id) {
        log.info("ImageRepository.findById: {}", id);

        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return Optional.ofNullable(imageMap.get(id));
        });
    }
}
