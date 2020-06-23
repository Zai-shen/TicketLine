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
     * Find all news entries that haven't been read by the logged in user yet, paginated and ordered by publishing date
     *
     * @param pageable Pagination information
     * @return ordered page of news entries
     */
    Page<News> findAllUnread(Pageable pageable);

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
     * @return published news entry id
     */
    Long publishNews(News news);

    /**
     * Add read news entry to the set of read news
     * @param newsId the read news id to be saved for the current user
     */
    void saveReadNewsForCurrentUser(Long newsId);

    /**
     * Save image in the public folder of our server for given news
     * @param newsId the news id of corresponding news
     * @param base64Image the image to be saved, encoded in base64
     */
    void saveImageForNewsWithId(Long newsId, String base64Image);

    /**
     * Get image of given news
     * @param newsId the id of the news of wanted picture
     * @return picture encoded in base64
     */
    String getImageOfNewsWithId(Long newsId);

}
