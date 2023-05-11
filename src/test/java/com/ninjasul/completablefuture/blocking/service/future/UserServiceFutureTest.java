
package com.ninjasul.completablefuture.blocking.service.future;

import com.ninjasul.completablefuture.blocking.repository.ArticleRepository;
import com.ninjasul.completablefuture.blocking.repository.FollowRepository;
import com.ninjasul.completablefuture.blocking.repository.ImageRepository;
import com.ninjasul.completablefuture.blocking.repository.UserRepository;
import com.ninjasul.completablefuture.blocking.service.UserBlockingService;
import com.ninjasul.completablefuture.common.User;
import com.ninjasul.completablefuture.future.repository.ArticleFutureRepository;
import com.ninjasul.completablefuture.future.repository.FollowFutureRepository;
import com.ninjasul.completablefuture.future.repository.ImageFutureRepository;
import com.ninjasul.completablefuture.future.repository.UserFutureRepository;
import com.ninjasul.completablefuture.future.service.UserFutureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserServiceFutureTest {
    UserFutureService userFutureService;
    UserFutureRepository userRepository;
    ArticleFutureRepository articleRepository;
    ImageFutureRepository imageRepository;
    FollowFutureRepository followRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserFutureRepository();
        articleRepository = new ArticleFutureRepository();
        imageRepository = new ImageFutureRepository();
        followRepository = new FollowFutureRepository();

        userFutureService = new UserFutureService(
            userRepository,
            articleRepository,
            imageRepository,
            followRepository
        );
    }

    @Test
    void getUserEmptyIfInvalidUserIdIsGiven() {
        // given
        String userId = "invalid_user_id";

        // when
        Optional<User> user = userFutureService.getUserById(userId);

        // then
        assertTrue(user.isEmpty());
    }

    @Test
    void testGetUser() {
        // given
        String userId = "1234";

        // when
        Optional<User> optionalUser = userFutureService.getUserById(userId);

        // then
        assertFalse(optionalUser.isEmpty());
        var user = optionalUser.get();
        assertEquals(user.getName(), "taewoo");
        assertEquals(user.getAge(), 32);

        assertFalse(user.getProfileImage().isEmpty());
        var image = user.getProfileImage().get();

        assertEquals(image.getId(), "image#1000");
        assertEquals(image.getName(), "profileImage");
        assertEquals(image.getUrl(), "https://dailyone.com/images/1000");

        assertEquals(2, user.getArticleList().size());
    }
}
