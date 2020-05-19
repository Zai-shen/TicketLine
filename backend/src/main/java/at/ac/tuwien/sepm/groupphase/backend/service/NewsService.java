package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NewsService {

    /**
     * Find all news entries of specified page ordered by published at date (descending).
     *
     * @param pageable Pagination information
     * @return ordered list of all news entries. Restricted by the pagination.
     */
    Page<News> findAll(Pageable pageable);

    /**
     * Find a single news entry by id.
     *
     * @param id the id of the news entry
     * @return the news entry
     */
    News findOne(Long id);

    /**
     * Publish a single news entry
     *
     * @param news to publish
     * @return published news entry
     */
    News publishNews(News news);

}
