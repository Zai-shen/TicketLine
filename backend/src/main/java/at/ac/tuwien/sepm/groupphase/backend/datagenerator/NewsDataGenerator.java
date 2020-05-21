package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;

@Profile("generateData")
@Component
public class NewsDataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final int NUMBER_OF_NEWS_TO_GENERATE = 5;
    private static final String TEST_NEWS_TITLE = "Title";
    private static final String TEST_NEWS_SUMMARY = "Summary of the news";
    private static final String TEST_NEWS_CONTENT = "This is the text of the news";

    private final NewsRepository newsRepository;

    public NewsDataGenerator(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @PostConstruct
    private void generateNews() {
        if (newsRepository.findAll().size() > 0) {
            LOGGER.debug("news already generated");
        } else {
            LOGGER.debug("generating {} news entries", NUMBER_OF_NEWS_TO_GENERATE);
            for (int i = 0; i < NUMBER_OF_NEWS_TO_GENERATE; i++) {
                News news = News.MessageBuilder.aMessage()
                    .withTitle(TEST_NEWS_TITLE + " " + i)
                    .withSummary(TEST_NEWS_SUMMARY + " " + i)
                    .withContent(TEST_NEWS_CONTENT + " " + i)
                    .withPublishedAt(LocalDateTime.now().minusMonths(i))
                    .build();
                LOGGER.debug("saving news {}", news);
                newsRepository.save(news);
            }
        }
    }

}
