package at.ac.tuwien.sepm.groupphase.backend.controller;

import at.ac.tuwien.sepm.groupphase.backend.api.NewsApi;
import at.ac.tuwien.sepm.groupphase.backend.controller.mapper.NewsMapper;
import at.ac.tuwien.sepm.groupphase.backend.dto.NewsDTO;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.security.AuthorizationRole;
import at.ac.tuwien.sepm.groupphase.backend.service.NewsService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class NewsController implements NewsApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final NewsMapper newsMapper;
    private final NewsService newsService;

    @Autowired
    public NewsController(NewsMapper newsMapper, NewsService newsService) {
        this.newsMapper = newsMapper;
        this.newsService = newsService;
    }

    @Override
    @Secured(AuthorizationRole.USER_ROLE)
    public ResponseEntity<NewsDTO> getNews(@PathVariable Long newsId) {
        LOGGER.info("Find news with id {}", newsId);
        return ResponseEntity.of(Optional.of(newsMapper.toDTO(newsService.findOne(newsId))));
    }

    @Override
    @Secured(AuthorizationRole.USER_ROLE)
    public ResponseEntity<List<NewsDTO>> getNewsList(@Valid Optional<Long> unreadBy,
        @Valid Optional<Integer> page) {
        LOGGER.info("Get all news");
        var DTOList = new ArrayList<NewsDTO>();
        var entityList = newsService.findAll();
        for (News n: entityList) {
            DTOList.add(newsMapper.toDTO(n));
        }
        return ResponseEntity.of(Optional.of(DTOList));
    }

    @Override
    @Secured(AuthorizationRole.ADMIN_ROLE)
    public ResponseEntity<Void> createNews(@Valid NewsDTO newsDTO) {
        LOGGER.info("Create news with title {}", newsDTO.getTitle());
        News news = newsMapper.toEntity(newsDTO);
        newsService.publishNews(news);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
