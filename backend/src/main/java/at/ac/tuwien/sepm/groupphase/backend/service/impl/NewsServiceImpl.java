package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.exception.GlobalExceptionHandler;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.exception.SaveFileException;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.NewsService;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import at.ac.tuwien.sepm.groupphase.backend.service.validator.NewNewsValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.lang.invoke.MethodHandles;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;
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
    public void saveImageForNewsWithId(Long newsId, String imageData){
        News currentNews = findOne(newsId);
        LOGGER.debug("Save image for news with id {}", newsId);
        currentNews.setPicturePath(UUID.randomUUID() + newsId.toString());
        String base64Image = imageData.split(",")[1];
        byte[] imageByte = Base64.getDecoder().decode(base64Image.getBytes(StandardCharsets.UTF_8));
        String path = Paths.get(System.getProperty("user.dir"), "public","images").toString();
        String filename = currentNews.getPicturePath() + ".png";
        Path destinationFile = Paths.get(path, filename);
        Path destinationPath = Paths.get(path);
        try {
            Files.createDirectories(destinationPath);
            Files.write(destinationFile, imageByte);
        }catch (IOException e){
            throw new SaveFileException(e);
        }

        newsRepository.saveAndFlush(currentNews);
    }

    @Override
    public String getImageOfNewsWithId(Long newsId) {
        News currentNews = findOne(newsId);
        LOGGER.debug("Get image for news with id {}", newsId);
        String location = Paths.get(System.getProperty("user.dir") ,"public","images", currentNews.getPicturePath() + ".png").toString();

        String base64File = "";
        File file = new File(location);

        try (FileInputStream imageInFile = new FileInputStream(file)) {
            byte fileData[] = new byte[(int) file.length()];
            imageInFile.read(fileData);
            base64File = Base64.getEncoder().encodeToString(fileData);
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        return "data:image/png;base64," + base64File;
    }
}
