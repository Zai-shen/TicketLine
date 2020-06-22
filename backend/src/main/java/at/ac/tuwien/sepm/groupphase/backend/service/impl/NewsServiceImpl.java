package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.exception.GlobalExceptionHandler;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.NewsService;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import at.ac.tuwien.sepm.groupphase.backend.service.validator.NewNewsValidator;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class NewsServiceImpl implements NewsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final UserService userService;
    private final NewsRepository newsRepository;

    public NewsServiceImpl(UserService userService, NewsRepository newsRepository) {
        this.userService = userService;
        this.newsRepository = newsRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<News> findAll(Pageable pageable) {
        LOGGER.debug("Find all news with page {}", pageable.getPageNumber());
        return newsRepository.findAllByOrderByPublishedAtDesc(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<News> findAllUnread(Pageable pageable) {
        LOGGER.debug("Find all unread for page {}", pageable.getPageNumber());
        User loggedInUser = userService.getCurrentLoggedInUser();

        return newsRepository.findAllUnreadNewsOfUser(loggedInUser, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public News findOne(Long id) {
        LOGGER.debug("Find news with id {}", id);
        Optional<News> news = newsRepository.findById(id);
        return news.orElseThrow(() -> new NotFoundException(String.format("News mit der ID %s wurde nicht gefunden", id)));
    }

    @Override
    public Long publishNews(News news) {
        LOGGER.debug("Publish new news {}", news);
        new NewNewsValidator().build(news).validate();
        news.setPublishedAt(LocalDateTime.now()); // overwrite publishedAt, so we can guarantee server-time
        return this.newsRepository.saveAndFlush(news).getId();
    }

    @Override
    @Transactional
    public void saveReadNewsForCurrentUser(Long newsId){
        LOGGER.debug("Save read news with id {} for current user", newsId);
        News news = findOne(newsId);
        User currentUser = userService.getCurrentLoggedInUser();

        news.getReadByUsers().add(currentUser);

        newsRepository.save(news);
    }

    @Override
    public String saveImageForNewsWithId(Long newsId, String base64Image){
        News currentNews = findOne(newsId);
        LOGGER.debug("Save image for news with id {}", newsId);

        //currentNews.setPicturePath("\\images\\" + (ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE)) + newsId + ".png");
        currentNews.setPicturePath((ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE)) + newsId.toString());

        //save image in directory
        byte[] imageByte = Base64.decodeBase64(base64Image);
        String directory = System.getProperty("user.dir") + "\\images\\" + currentNews.getPicturePath() + ".png";
        try (OutputStream stream = new FileOutputStream(directory)) {
            stream.write(imageByte);
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        //update current news
        newsRepository.saveAndFlush(currentNews);

        return currentNews.getPicturePath();
    }


}
