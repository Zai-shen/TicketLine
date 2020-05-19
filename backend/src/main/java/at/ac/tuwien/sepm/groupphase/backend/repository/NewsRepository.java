package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    /**
     * Find all news entries ordered by published at date (descending).
     *
     * @return ordered list of all news entries
     */
    Page<News> findAllByOrderByPublishedAtDesc(Pageable pageable);

}
