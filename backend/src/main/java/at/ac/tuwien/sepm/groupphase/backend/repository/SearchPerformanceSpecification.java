package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.SearchPerformance;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class SearchPerformanceSpecification implements Specification<Performance> {

    private static final int MINUTES_OFFSET = 30;
    private SearchPerformance filter;

    public SearchPerformanceSpecification(SearchPerformance filter) {
        super();
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<Performance> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();
        List<Expression<Boolean>> searchCriteria = predicate.getExpressions();

        if (filter.getDate() != null) {
            LocalDate filterDate = filter.getDate();
            OffsetDateTime startDate = OffsetDateTime.of(filterDate, LocalTime.of(0,0), ZoneOffset.UTC);
            OffsetDateTime endDate = OffsetDateTime.of(filterDate, LocalTime.of(23,59), ZoneOffset.UTC);
            Expression<Boolean> dateSearch = criteriaBuilder.between(root.get("dateTime"), startDate, endDate);
            searchCriteria.add(dateSearch);
        }
        if (filter.getTime() != null) {
            LocalTime filterTime = filter.getTime();
            LocalTime startTime = filterTime.plusMinutes(30);
            Expression<Boolean> timeSearch = criteriaBuilder.equal(criteriaBuilder.function("TO_CHAR", Time.class,
                (root.get("dateTime")), criteriaBuilder.literal("HH:MM")), filterTime.plusMinutes(30));
            searchCriteria.add(timeSearch);
        }

        if (filter.getEvent() != null) {
            Join<Performance,Event> eventJoin = root.join("event");
            Expression<Boolean> eventSearch = criteriaBuilder.like(criteriaBuilder.lower(eventJoin.get("title")),
                "%" + filter.getEvent().toLowerCase() + "%");
            searchCriteria.add(eventSearch);
        }
        return predicate;
    }

}
