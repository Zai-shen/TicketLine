package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.SearchPerformance;
import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class SearchPerformanceSpecification implements Specification<Performance> {

    @PersistenceContext
    EntityManager entityManager;

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
        if (!filter.getTime().equals("")) {
            LocalTime filterTime = LocalTime.parse(filter.getTime());
            Time startTime = Time.valueOf(filterTime.minusMinutes(MINUTES_OFFSET));
            Time endTime = Time.valueOf(filterTime.plusMinutes(MINUTES_OFFSET));
            Expression<Boolean> timeSearch = criteriaBuilder.between(criteriaBuilder.function("FORMATDATETIME",
                Time.class, root.get("dateTime"), criteriaBuilder.literal("HH:mm")),
                startTime, endTime);
            searchCriteria.add(timeSearch);
        }
        if (filter.getEvent() != null) {
            Join<Performance, Event> eventJoin = root.join("event");
            Expression<Boolean> eventSearch = criteriaBuilder.like(criteriaBuilder.lower(eventJoin.get("title")),
                "%" + filter.getEvent().toLowerCase() + "%");
            searchCriteria.add(eventSearch);
        }
        if (filter.getPrice() != null) {
            Join<Performance, Seat> seatJoin = root.join("location").join("seatmap")
                .join("seatGroupAreas").join("seats");

            Subquery<Performance> subquery = criteriaQuery.subquery(Performance.class);
            subquery.select(root.join("location").join("seatmap")
                .join("seatGroupAreas").join("seats"));
            subquery.where(criteriaBuilder.lessThan(seatJoin.get("price"), filter.getPrice().doubleValue()));
            Expression<Boolean> test = criteriaBuilder.exists(subquery);


            Expression<Boolean> priceSearch = criteriaBuilder.between(seatJoin.get("price"),
                filter.getPrice().doubleValue()-10, filter.getPrice().doubleValue()+10);

            searchCriteria.add(priceSearch);
        }
        return predicate;
    }

}
