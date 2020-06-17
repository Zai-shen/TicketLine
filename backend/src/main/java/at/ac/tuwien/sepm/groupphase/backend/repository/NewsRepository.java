package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    /**
     * Find all news entries ordered by published at date (descending).
     *
     * @return ordered list of all news entries
     */
    Page<News> findAllByOrderByPublishedAtDesc(Pageable pageable);

    @Query("SELECT n FROM News n WHERE n NOT IN (SELECT n FROM News n JOIN n.readByUsers u WHERE u = ?1) ORDER BY n.publishedAt DESC")
    Page<News> findAllUnreadNewsOfUser(User user, Pageable pageable);

}
