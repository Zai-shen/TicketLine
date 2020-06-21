package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.List;

public class SearchEventSpecification implements Specification<Event> {

    private static final int DURATION_OFFSET = 30;
    private Event filter;

    public SearchEventSpecification(Event filter) {
        super();
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();
        List<Expression<Boolean>> searchCriteria = predicate.getExpressions();

        if (filter.getDescription() != null) {
            Expression<Boolean> descriptionSearch = criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),
                "%" + filter.getDescription().toLowerCase() + "%");
            searchCriteria.add(descriptionSearch);
        }
        if (filter.getTitle() != null) {
            Expression<Boolean> titleSearch = criteriaBuilder.like(criteriaBuilder.lower(root.get("title")),
                "%" + filter.getTitle().toLowerCase() + "%");
            searchCriteria.add(titleSearch);
        }
        if (filter.getCategory() != null) {
            Expression<Boolean> categorySearch = criteriaBuilder.equal(root.get("category"), filter.getCategory());
            searchCriteria.add(categorySearch);
        }
        if (filter.getDuration() != null) {
            long filterDuration = filter.getDuration();
            Expression<Boolean> durationSearch =
                criteriaBuilder.between(root.get("duration"), filterDuration - DURATION_OFFSET,
                    filterDuration + DURATION_OFFSET);
            searchCriteria.add(durationSearch);
        }
        if (filter.getArtist() != null) {
            Expression<Boolean> artistSearch = criteriaBuilder.equal(root.get("artist"),filter.getArtist());
            searchCriteria.add(artistSearch);
        }

        return predicate;
    }
}
