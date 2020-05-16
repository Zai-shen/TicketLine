package at.ac.tuwien.sepm.groupphase.backend.controller;

import at.ac.tuwien.sepm.groupphase.backend.api.NewsApi;
import at.ac.tuwien.sepm.groupphase.backend.controller.mapper.NewsMapper;
import at.ac.tuwien.sepm.groupphase.backend.dto.NewsDTO;
import at.ac.tuwien.sepm.groupphase.backend.dto.NewsSummaryDTO;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.service.NewsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class NewsController implements NewsApi {

    private final NewsMapper newsMapper;
    private final NewsService newsService;

    @Autowired
    public NewsController(NewsMapper newsMapper, NewsService newsService) {
        this.newsMapper = newsMapper;
        this.newsService = newsService;
    }

    @Override
    @GetMapping("/news")
    @ApiOperation("Returns list of News")
    public ResponseEntity<List<NewsSummaryDTO>> getNewsList(@Valid Optional<Long> unreadBy,
        @Valid Optional<Integer> page) {
        var list = new ArrayList<NewsSummaryDTO>();
        list.add(new NewsSummaryDTO().title("News!").id(42L).created(LocalDate.now()).summary("foobar"));
        return ResponseEntity.of(Optional.of(list));
    }

//    @Override
//    public ResponseEntity<Void> register(@Valid UserDTO userDTO) {
//        User user = userMapper.toEntity(userDTO);
//        userService.register(user);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }

    @Override
    @PostMapping("/news")
    @ApiOperation("Create a new news entry - Admin task")
    public ResponseEntity<Void> createNews(@Valid NewsDTO newsDTO) {
        News news = newsMapper.toEntity(newsDTO);
        newsService.publishNews(news);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
