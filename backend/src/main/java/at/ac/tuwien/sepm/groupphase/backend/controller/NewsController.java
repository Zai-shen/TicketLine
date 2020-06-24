package at.ac.tuwien.sepm.groupphase.backend.controller;

import at.ac.tuwien.sepm.groupphase.backend.api.NewsApi;
import at.ac.tuwien.sepm.groupphase.backend.controller.mapper.NewsMapper;
import at.ac.tuwien.sepm.groupphase.backend.dto.InlineResponse200;
import at.ac.tuwien.sepm.groupphase.backend.dto.NewsDTO;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.security.AuthorizationRole;
import at.ac.tuwien.sepm.groupphase.backend.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;

@Controller
public class NewsController implements NewsApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final NewsMapper newsMapper;
    private final NewsService newsService;

    private static final int PAGE_SIZE = 25;

    @Autowired
    public NewsController(NewsMapper newsMapper, NewsService newsService) {
        this.newsMapper = newsMapper;
        this.newsService = newsService;
    }

    @Override
    @Secured(AuthorizationRole.USER_ROLE)
    public ResponseEntity<NewsDTO> getNews(@PathVariable Long newsId) {
        LOGGER.info("Find news with id {}", newsId);
        return ResponseEntity.ok(newsMapper.toDTO(newsService.findOne(newsId)));
    }

    @Override
    @Secured(AuthorizationRole.USER_ROLE)
    public ResponseEntity<List<NewsDTO>> getNewsList(@Valid Optional<Boolean> inclRead,
        @Valid Optional<Integer> page) {
        LOGGER.info("Get all news, include read ones: {}", inclRead);

        Page<News> news;

        if (inclRead.orElse(false)) {
            news = newsService.findAll(PageRequest.of(page.orElse(0), PAGE_SIZE));
        } else {
            news = newsService.findAllUnread(PageRequest.of(page.orElse(0), PAGE_SIZE));
        }
        return ResponseEntity.ok()
            .header("X-Total-Count", String.valueOf(news.getTotalElements()))
            .body(newsMapper.toDtoList(news.getContent()));
    }

    @Override
    @Secured(AuthorizationRole.ADMIN_ROLE)
    public ResponseEntity<Long> createNews(@Valid NewsDTO newsDTO) {
        LOGGER.info("Create news with title {}", newsDTO.getTitle());
        return ResponseEntity.status(HttpStatus.CREATED).body(
            newsService.publishNews(newsMapper.toEntity(newsDTO)));
    }

    @Override
    @Secured(AuthorizationRole.ADMIN_ROLE)
    public ResponseEntity<Void> uploadPictureForNews(Long newsId, @Valid String base64Image) {
        LOGGER.info("Save image for news with id {}", newsId);
        newsService.saveImageForNewsWithId(newsId, base64Image);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<InlineResponse200> getPictureOfNews(Long newsId) {
        LOGGER.info("Get image for news with id {}", newsId);

        return ResponseEntity.status(HttpStatus.OK).body(
            new InlineResponse200().picture(newsService.getImageOfNewsWithId(newsId)));
    }
}
