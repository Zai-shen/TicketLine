package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
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

    private static final int MINUTES_OFFSET = 30;
    private static final int PRICE_OFFSET = 5;
    private SearchPerformance filter;

    @PersistenceContext
    private EntityManager entityManager;

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
        /*
        SELECT * FROM PERFORMANCE
        WHERE PERFORMANCE.ID IN (SELECT PERFORMANCE.ID FROM PERFORMANCE
         INNER JOIN LOCATION
          ON PERFORMANCE.LOCATION_ID = LOCATION.ID
         INNER JOIN SEATMAP
          ON SEATMAP.ID = LOCATION.SEATMAP_ID
         INNER JOIN SEAT_GROUP_AREA
          ON SEAT_GROUP_AREA.SEATMAP_ID = SEATMAP.ID
         INNER JOIN SEAT
          ON SEAT.SEATGROUP_ID = SEAT_GROUP_AREA.ID
        AND SEAT.price < 20
        GROUP BY PERFORMANCE.ID
        )
         */
        if (filter.getPrice() != null) {
            Join<Performance, Seat> seatJoin = root.join("location").join("seatmap")
                .join("seatGroupAreas").join("seats");

            Subquery<Performance> subquery = criteriaQuery.subquery(Performance.class);
            Root<Performance> performances = subquery.from(Performance.class);
            subquery.select(performances)
                .distinct(true)
                .where(criteriaBuilder.between(seatJoin.get("price"), filter.getPrice().doubleValue() - PRICE_OFFSET,
                    filter.getPrice().doubleValue() + PRICE_OFFSET));
            Expression<Boolean> priceSearch = criteriaBuilder.in(root.get("id")).value(subquery);
            searchCriteria.add(priceSearch);
        }
        if (filter.getLocation() != null) {
            Join<Performance, Location> locationJoin = root.join("location");
            Expression<Boolean> locationSearch = criteriaBuilder.like(criteriaBuilder.lower(locationJoin.get("description")),
                "%" + filter.getLocation().toLowerCase() + "%");
            searchCriteria.add(locationSearch);
        }
        return predicate;
    }

}
