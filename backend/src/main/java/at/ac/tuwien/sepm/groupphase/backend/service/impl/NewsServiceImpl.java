package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.NewsService;
import at.ac.tuwien.sepm.groupphase.backend.service.validator.NewNewsValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NewsServiceImpl implements NewsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final NewsRepository newsRepository;

    public NewsServiceImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public Page<News> findAll(Pageable pageable) {
        LOGGER.debug("Find all news with page {}", pageable.getPageNumber());
        return newsRepository.findAllByOrderByPublishedAtDesc(pageable);
    }

    @Override
    public News findOne(Long id) {
        LOGGER.debug("Find news with id {}", id);
        Optional<News> news = newsRepository.findById(id);
        return news.orElseThrow(() -> new NotFoundException(String.format("News mit der ID %s wurde nicht gefunden", id)));
    }

    @Override
    public News publishNews(News news) {
        LOGGER.debug("Publish new news {}", news);
        new NewNewsValidator().build(news).validate();
        news.setPublishedAt(LocalDateTime.now()); // overwrite publishedAt, so we can guarantee server-time
        return newsRepository.save(news);
    }

}
