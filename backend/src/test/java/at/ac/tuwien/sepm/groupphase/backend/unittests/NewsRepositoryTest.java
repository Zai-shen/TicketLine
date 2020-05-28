package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
// This test slice annotation is used instead of @SpringBootTest to load only repository beans instead of
// the entire application context
@DataJpaTest
@ActiveProfiles("test")
public class NewsRepositoryTest implements TestData {

    @Autowired
    private NewsRepository newsRepository;

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

}
