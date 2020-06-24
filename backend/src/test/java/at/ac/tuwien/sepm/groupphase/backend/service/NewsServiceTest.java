package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.exception.BusinessValidationException;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.impl.NewsServiceImpl;
import at.ac.tuwien.sepm.groupphase.backend.util.DomainTestObjectFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NewsServiceTest {

    @Mock
    private NewsRepository newsRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private NewsServiceImpl newsService;

    @Test
    public void testFindNewsNonExisting() {
        assertThatThrownBy(() -> newsService.findOne(null)).isExactlyInstanceOf(
            NotFoundException.class);
    }

    @Test
    public void testPublishNewsWithValidInput() {
        final News news = DomainTestObjectFactory.getNews();
        when(newsRepository.saveAndFlush(news)).thenReturn(news);

        newsService.publishNews(news);

        verify(newsRepository, times(1)).saveAndFlush(news);
    }

    @Test
    public void testPublishNewsWithInvalidInput() {
        final News news = new News();

        assertThatThrownBy(() -> newsService.publishNews(news)).isExactlyInstanceOf(
            BusinessValidationException.class);
        verify(newsRepository, times(0)).save(news);
    }

    @Test
    public void testSaveReadNewsForCurrentUser() {
        final News news = DomainTestObjectFactory.getNews();
        final User user = DomainTestObjectFactory.getUser();

        when(newsRepository.findById(any())).thenReturn(Optional.of(news));
        when(userService.getCurrentLoggedInUser()).thenReturn(user);

        newsService.saveReadNewsForCurrentUser(7L);

        assertThat(news.getReadByUsers().contains(user)).isTrue();
        verify(newsRepository, times(1)).save(news);
    }

}
