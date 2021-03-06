package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.util.DomainTestObjectFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
// This test slice annotation is used instead of @SpringBootTest to load only repository beans instead of
// the entire application context
@DataJpaTest
@ActiveProfiles("test")
public class NewsRepositoryTest {

    String TEST_NEWS_TITLE = "Title";
    String TEST_NEWS_SUMMARY = "Summary";
    String TEST_NEWS_CONTENT = "TestMessageText";
    String TEST_NEWS_AUTHOR = "Doris Duftler";

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void givenNothing_whenSaveNews_thenFindListWithOneElementAndFindNewsById() {
        News news = new News();
        news.setTitle(TEST_NEWS_TITLE + " " + 0);
        news.setSummary(TEST_NEWS_SUMMARY + " " + 0);
        news.setContent(TEST_NEWS_CONTENT + " " + 0);
        news.setPublishedAt(LocalDateTime.now().minusMonths(10));
        news.setAuthor(TEST_NEWS_AUTHOR);

        newsRepository.save(news);

        assertAll(
            () -> assertEquals(1, newsRepository.findAll().size()),
            () -> assertNotNull(newsRepository.findById(news.getId()))
        );
    }

    @Test
    public void givenNothing_whenSaveMultipleNews_thenFindListWithMultipleElementsAndFindTheseNewsById() {
        News news = new News();
        news.setTitle(TEST_NEWS_TITLE + " " + 1);
        news.setSummary(TEST_NEWS_SUMMARY + " " + 1);
        news.setContent(TEST_NEWS_CONTENT + " " + 1);
        news.setPublishedAt(LocalDateTime.now().minusMonths(1));
        news.setAuthor(TEST_NEWS_AUTHOR);

        News newsTwo = new News();
        newsTwo.setTitle(TEST_NEWS_TITLE + " " + 2);
        newsTwo.setSummary(TEST_NEWS_SUMMARY + " " + 2);
        newsTwo.setContent(TEST_NEWS_CONTENT + " " + 2);
        newsTwo.setPublishedAt(LocalDateTime.now().minusMonths(2));
        newsTwo.setAuthor(TEST_NEWS_AUTHOR);

        News newsThree = new News();
        newsThree.setTitle(TEST_NEWS_TITLE + " " + 3);
        newsThree.setSummary(TEST_NEWS_SUMMARY + " " + 3);
        newsThree.setContent(TEST_NEWS_CONTENT + " " + 3);
        newsThree.setPublishedAt(LocalDateTime.now().minusMonths(3));
        newsThree.setAuthor(TEST_NEWS_AUTHOR);

        newsRepository.save(news);
        newsRepository.save(newsTwo);
        newsRepository.save(newsThree);

        assertAll(
            () -> assertEquals(3, newsRepository.findAll().size()),
            () -> assertNotNull(newsRepository.findById(news.getId())),
            () -> assertNotNull(newsRepository.findById(newsTwo.getId())),
            () -> assertNotNull(newsRepository.findById(newsThree.getId()))
        );
    }

    @Test
    public void testFindAllUnreadNewsOfUser() {
        final News news = DomainTestObjectFactory.getNews();
        news.setId(null);
        final News unreadNews = DomainTestObjectFactory.getNews();
        unreadNews.setId(null);
        final User user = DomainTestObjectFactory.getUser();
        user.setId(null);
        final User otherUser = DomainTestObjectFactory.getUser();
        otherUser.setEmail("otherTestUser@example.com");
        otherUser.setId(null);

        userRepository.save(otherUser);
        userRepository.saveAndFlush(user);
        news.getReadByUsers().add(user);
        news.getReadByUsers().add(otherUser);
        unreadNews.getReadByUsers().add(otherUser);
        newsRepository.save(news);
        newsRepository.saveAndFlush(unreadNews);

        final Page<News> result = newsRepository.findAllUnreadNewsOfUser(user, Pageable.unpaged());

        assertThat(result).containsExactly(unreadNews);
    }

}
