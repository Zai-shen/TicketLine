package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.News;
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

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NewsServiceTest {

    @Mock
    private NewsRepository newsRepository;
    @InjectMocks
    private NewsServiceImpl newsService;

    @Test
    public void testFindNewsNonExisting() {
        assertThatThrownBy(() -> newsService.findOne(99999L)).isExactlyInstanceOf(
            NotFoundException.class);
    }

    @Test
    public void testPublishNewsWithValidInput() {
        final News news = DomainTestObjectFactory.getNews();

        newsService.publishNews(news);

        verify(newsRepository, times(1)).save(news);
    }

    @Test
    public void testPublishNewsWithInvalidInput() {
        final News news = new News();

        assertThatThrownBy(() -> newsService.publishNews(news)).isExactlyInstanceOf(
            BusinessValidationException.class);
        verify(newsRepository, times(0)).save(news);
    }

}
