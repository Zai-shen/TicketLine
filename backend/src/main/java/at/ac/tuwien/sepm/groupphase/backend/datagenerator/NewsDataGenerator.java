package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.Locale;

@Profile("generateData")
@Component
public class NewsDataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final int NUMBER_OF_NEWS_TO_GENERATE = 15;

    private final NewsRepository newsRepository;

    public NewsDataGenerator(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @PostConstruct
    private void generateNews() {
        if (!newsRepository.findAll().isEmpty()) {
            LOGGER.debug("news already generated");
        } else {
            LOGGER.debug("generating {} news entries", NUMBER_OF_NEWS_TO_GENERATE);
            Faker faker = new Faker(new Locale("de-AT"));
            for (int i = 0; i < NUMBER_OF_NEWS_TO_GENERATE; i++) {
                News news = new News();
                news.setTitle(faker.book().title());
                news.setSummary(faker.rickAndMorty().quote());
                news.setContent(faker.buffy().quotes());
                news.setPublishedAt(LocalDateTime.now().minusMonths(i));
                news.setAuthor(faker.name().fullName());
                LOGGER.debug("saving news {}", news);
                newsRepository.save(news);
            }
        }
    }

}
