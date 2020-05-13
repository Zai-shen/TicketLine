package at.ac.tuwien.sepm.groupphase.backend.controller;

import at.ac.tuwien.sepm.groupphase.backend.api.NewsApi;
import at.ac.tuwien.sepm.groupphase.backend.dto.NewsSummaryDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/v1")
public class NewsController implements NewsApi {
    @Override
    @GetMapping("/news")
    @ApiOperation("Returns list of News")
    public ResponseEntity<List<NewsSummaryDTO>> getNewsList(@Valid Optional<Long> unreadBy,
        @Valid Optional<Integer> page) {
        var list = new ArrayList<NewsSummaryDTO>();
        list.add(new NewsSummaryDTO().title("News!").id(42L).created(LocalDate.now()).summary("foobar"));
        return ResponseEntity.of(Optional.of(list));
    }
}
