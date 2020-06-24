package at.ac.tuwien.sepm.groupphase.backend.util;

import at.ac.tuwien.sepm.groupphase.backend.controller.mapper.LocationMapper;
import at.ac.tuwien.sepm.groupphase.backend.controller.mapper.LocationMapperImpl;
import at.ac.tuwien.sepm.groupphase.backend.dto.SeatmapDTO;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.security.AuthorizationRole;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.HashSet;

public class DomainTestObjectFactory {

    private static final String RAW_SEATMAP =
        "{\"seatGroupAreas\":[{\"x\":181.0,\"y\":223.0,\"width\":102.0,\"height\":105.0,\"price\":15.0,\"name\":\"Bereich-1\",\"seats\":[{\"x\":23.0,\"y\":23.0,\"rowLabel\":\"a\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":23.0,\"rowLabel\":\"a\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":51.0,\"y\":23.0,\"rowLabel\":\"a\",\"colLabel\":\"3\",\"radius\":5.0},{\"x\":65.0,\"y\":23.0,\"rowLabel\":\"a\",\"colLabel\":\"4\",\"radius\":5.0},{\"x\":79.0,\"y\":23.0,\"rowLabel\":\"a\",\"colLabel\":\"5\",\"radius\":5.0},{\"x\":93.0,\"y\":23.0,\"rowLabel\":\"a\",\"colLabel\":\"6\",\"radius\":5.0},{\"x\":23.0,\"y\":37.0,\"rowLabel\":\"b\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":37.0,\"rowLabel\":\"b\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":51.0,\"y\":37.0,\"rowLabel\":\"b\",\"colLabel\":\"3\",\"radius\":5.0},{\"x\":65.0,\"y\":37.0,\"rowLabel\":\"b\",\"colLabel\":\"4\",\"radius\":5.0},{\"x\":79.0,\"y\":37.0,\"rowLabel\":\"b\",\"colLabel\":\"5\",\"radius\":5.0},{\"x\":93.0,\"y\":37.0,\"rowLabel\":\"b\",\"colLabel\":\"6\",\"radius\":5.0},{\"x\":23.0,\"y\":51.0,\"rowLabel\":\"c\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":51.0,\"rowLabel\":\"c\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":51.0,\"y\":51.0,\"rowLabel\":\"c\",\"colLabel\":\"3\",\"radius\":5.0},{\"x\":65.0,\"y\":51.0,\"rowLabel\":\"c\",\"colLabel\":\"4\",\"radius\":5.0},{\"x\":79.0,\"y\":51.0,\"rowLabel\":\"c\",\"colLabel\":\"5\",\"radius\":5.0},{\"x\":93.0,\"y\":51.0,\"rowLabel\":\"c\",\"colLabel\":\"6\",\"radius\":5.0},{\"x\":23.0,\"y\":65.0,\"rowLabel\":\"d\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":65.0,\"rowLabel\":\"d\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":51.0,\"y\":65.0,\"rowLabel\":\"d\",\"colLabel\":\"3\",\"radius\":5.0},{\"x\":65.0,\"y\":65.0,\"rowLabel\":\"d\",\"colLabel\":\"4\",\"radius\":5.0},{\"x\":79.0,\"y\":65.0,\"rowLabel\":\"d\",\"colLabel\":\"5\",\"radius\":5.0},{\"x\":93.0,\"y\":65.0,\"rowLabel\":\"d\",\"colLabel\":\"6\",\"radius\":5.0},{\"x\":23.0,\"y\":79.0,\"rowLabel\":\"e\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":79.0,\"rowLabel\":\"e\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":51.0,\"y\":79.0,\"rowLabel\":\"e\",\"colLabel\":\"3\",\"radius\":5.0},{\"x\":65.0,\"y\":79.0,\"rowLabel\":\"e\",\"colLabel\":\"4\",\"radius\":5.0},{\"x\":79.0,\"y\":79.0,\"rowLabel\":\"e\",\"colLabel\":\"5\",\"radius\":5.0},{\"x\":93.0,\"y\":79.0,\"rowLabel\":\"e\",\"colLabel\":\"6\",\"radius\":5.0},{\"x\":23.0,\"y\":93.0,\"rowLabel\":\"f\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":93.0,\"rowLabel\":\"f\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":51.0,\"y\":93.0,\"rowLabel\":\"f\",\"colLabel\":\"3\",\"radius\":5.0},{\"x\":65.0,\"y\":93.0,\"rowLabel\":\"f\",\"colLabel\":\"4\",\"radius\":5.0},{\"x\":79.0,\"y\":93.0,\"rowLabel\":\"f\",\"colLabel\":\"5\",\"radius\":5.0},{\"x\":93.0,\"y\":93.0,\"rowLabel\":\"f\",\"colLabel\":\"6\",\"radius\":5.0}],\"seatLabels\":[{\"x\":23.0,\"y\":9.0,\"size\":5.0,\"text\":\"1\"},{\"x\":37.0,\"y\":9.0,\"size\":5.0,\"text\":\"2\"},{\"x\":51.0,\"y\":9.0,\"size\":5.0,\"text\":\"3\"},{\"x\":65.0,\"y\":9.0,\"size\":5.0,\"text\":\"4\"},{\"x\":79.0,\"y\":9.0,\"size\":5.0,\"text\":\"5\"},{\"x\":93.0,\"y\":9.0,\"size\":5.0,\"text\":\"6\"},{\"x\":9.0,\"y\":23.0,\"size\":5.0,\"text\":\"a\"},{\"x\":9.0,\"y\":37.0,\"size\":5.0,\"text\":\"b\"},{\"x\":9.0,\"y\":51.0,\"size\":5.0,\"text\":\"c\"},{\"x\":9.0,\"y\":65.0,\"size\":5.0,\"text\":\"d\"},{\"x\":9.0,\"y\":79.0,\"size\":5.0,\"text\":\"e\"},{\"x\":9.0,\"y\":93.0,\"size\":5.0,\"text\":\"f\"}]},{\"x\":284.0,\"y\":111.0,\"width\":53.0,\"height\":219.0,\"price\":11.0,\"name\":\"Bereich-2\",\"seats\":[{\"x\":23.0,\"y\":23.0,\"rowLabel\":\"a\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":23.0,\"rowLabel\":\"a\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":37.0,\"rowLabel\":\"b\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":37.0,\"rowLabel\":\"b\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":51.0,\"rowLabel\":\"c\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":51.0,\"rowLabel\":\"c\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":65.0,\"rowLabel\":\"d\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":65.0,\"rowLabel\":\"d\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":79.0,\"rowLabel\":\"e\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":79.0,\"rowLabel\":\"e\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":93.0,\"rowLabel\":\"f\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":93.0,\"rowLabel\":\"f\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":107.0,\"rowLabel\":\"g\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":107.0,\"rowLabel\":\"g\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":121.0,\"rowLabel\":\"h\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":121.0,\"rowLabel\":\"h\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":135.0,\"rowLabel\":\"i\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":135.0,\"rowLabel\":\"i\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":149.0,\"rowLabel\":\"j\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":149.0,\"rowLabel\":\"j\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":163.0,\"rowLabel\":\"k\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":163.0,\"rowLabel\":\"k\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":177.0,\"rowLabel\":\"l\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":177.0,\"rowLabel\":\"l\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":191.0,\"rowLabel\":\"m\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":191.0,\"rowLabel\":\"m\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":205.0,\"rowLabel\":\"n\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":205.0,\"rowLabel\":\"n\",\"colLabel\":\"2\",\"radius\":5.0}],\"seatLabels\":[{\"x\":23.0,\"y\":9.0,\"size\":5.0,\"text\":\"1\"},{\"x\":37.0,\"y\":9.0,\"size\":5.0,\"text\":\"2\"},{\"x\":9.0,\"y\":23.0,\"size\":5.0,\"text\":\"a\"},{\"x\":9.0,\"y\":37.0,\"size\":5.0,\"text\":\"b\"},{\"x\":9.0,\"y\":51.0,\"size\":5.0,\"text\":\"c\"},{\"x\":9.0,\"y\":65.0,\"size\":5.0,\"text\":\"d\"},{\"x\":9.0,\"y\":79.0,\"size\":5.0,\"text\":\"e\"},{\"x\":9.0,\"y\":93.0,\"size\":5.0,\"text\":\"f\"},{\"x\":9.0,\"y\":107.0,\"size\":5.0,\"text\":\"g\"},{\"x\":9.0,\"y\":121.0,\"size\":5.0,\"text\":\"h\"},{\"x\":9.0,\"y\":135.0,\"size\":5.0,\"text\":\"i\"},{\"x\":9.0,\"y\":149.0,\"size\":5.0,\"text\":\"j\"},{\"x\":9.0,\"y\":163.0,\"size\":5.0,\"text\":\"k\"},{\"x\":9.0,\"y\":177.0,\"size\":5.0,\"text\":\"l\"},{\"x\":9.0,\"y\":191.0,\"size\":5.0,\"text\":\"m\"},{\"x\":9.0,\"y\":205.0,\"size\":5.0,\"text\":\"n\"}]},{\"x\":129.0,\"y\":107.0,\"width\":51.0,\"height\":225.0,\"price\":11.0,\"name\":\"Bereich-3\",\"seats\":[{\"x\":23.0,\"y\":23.0,\"rowLabel\":\"a\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":23.0,\"rowLabel\":\"a\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":37.0,\"rowLabel\":\"b\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":37.0,\"rowLabel\":\"b\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":51.0,\"rowLabel\":\"c\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":51.0,\"rowLabel\":\"c\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":65.0,\"rowLabel\":\"d\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":65.0,\"rowLabel\":\"d\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":79.0,\"rowLabel\":\"e\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":79.0,\"rowLabel\":\"e\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":93.0,\"rowLabel\":\"f\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":93.0,\"rowLabel\":\"f\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":107.0,\"rowLabel\":\"g\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":107.0,\"rowLabel\":\"g\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":121.0,\"rowLabel\":\"h\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":121.0,\"rowLabel\":\"h\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":135.0,\"rowLabel\":\"i\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":135.0,\"rowLabel\":\"i\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":149.0,\"rowLabel\":\"j\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":149.0,\"rowLabel\":\"j\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":163.0,\"rowLabel\":\"k\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":163.0,\"rowLabel\":\"k\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":177.0,\"rowLabel\":\"l\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":177.0,\"rowLabel\":\"l\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":191.0,\"rowLabel\":\"m\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":191.0,\"rowLabel\":\"m\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":205.0,\"rowLabel\":\"n\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":205.0,\"rowLabel\":\"n\",\"colLabel\":\"2\",\"radius\":5.0},{\"x\":23.0,\"y\":219.0,\"rowLabel\":\"o\",\"colLabel\":\"1\",\"radius\":5.0},{\"x\":37.0,\"y\":219.0,\"rowLabel\":\"o\",\"colLabel\":\"2\",\"radius\":5.0}],\"seatLabels\":[{\"x\":23.0,\"y\":9.0,\"size\":5.0,\"text\":\"1\"},{\"x\":37.0,\"y\":9.0,\"size\":5.0,\"text\":\"2\"},{\"x\":9.0,\"y\":23.0,\"size\":5.0,\"text\":\"a\"},{\"x\":9.0,\"y\":37.0,\"size\":5.0,\"text\":\"b\"},{\"x\":9.0,\"y\":51.0,\"size\":5.0,\"text\":\"c\"},{\"x\":9.0,\"y\":65.0,\"size\":5.0,\"text\":\"d\"},{\"x\":9.0,\"y\":79.0,\"size\":5.0,\"text\":\"e\"},{\"x\":9.0,\"y\":93.0,\"size\":5.0,\"text\":\"f\"},{\"x\":9.0,\"y\":107.0,\"size\":5.0,\"text\":\"g\"},{\"x\":9.0,\"y\":121.0,\"size\":5.0,\"text\":\"h\"},{\"x\":9.0,\"y\":135.0,\"size\":5.0,\"text\":\"i\"},{\"x\":9.0,\"y\":149.0,\"size\":5.0,\"text\":\"j\"},{\"x\":9.0,\"y\":163.0,\"size\":5.0,\"text\":\"k\"},{\"x\":9.0,\"y\":177.0,\"size\":5.0,\"text\":\"l\"},{\"x\":9.0,\"y\":191.0,\"size\":5.0,\"text\":\"m\"},{\"x\":9.0,\"y\":205.0,\"size\":5.0,\"text\":\"n\"},{\"x\":9.0,\"y\":219.0,\"size\":5.0,\"text\":\"o\"}]}],\"standingAreas\":[{\"x\":182.0,\"y\":112.0,\"width\":100.0,\"height\":109.0,\"name\":\"Bereich-0\",\"maxPeople\":10,\"price\":10.0}]}";

    public static User getUser() {
        final User user = new User();
        user.setAddress(getAddress());
        user.setRole(AuthorizationRole.USER);
        user.setFirstname("Hans");
        user.setLastname("Müller");
        user.setEmail("hans.mueller@example.com");
        user.setPassword("secretPassword");
        user.setId(14L);
        user.setLocked(false);
        user.setWrongAttempts(1);
        return user;
    }

    public static Address getAddress() {
        final Address address = new Address();
        address.setStreet("Hauptstraße");
        address.setHousenr("24A");
        address.setPostalcode("3100");
        address.setCity("St. Pölten");
        address.setCountry("Österreich");
        return address;
    }

    public static Seatmap getSeatmap() {
        ObjectMapper om = new ObjectMapper();
        LocationMapper lm = new LocationMapperImpl();
        SeatmapDTO sdto = null;
        try {
            sdto = om.readValue(RAW_SEATMAP, SeatmapDTO.class);
        } catch (JsonProcessingException e) {
            // ignored since json is valid
        }
        Seatmap sm = lm.fromDto(sdto);
        for (SeatGroupArea sga : sm.getSeatGroupAreas()) {
            for (Seat s : sga.getSeats()) {
                s.setSeatGroupArea(sga);
            }
        }
        return sm;
    }

    public static News getNews() {
        final News news = new News();
        news.setId(16L);
        news.setTitle("Breaking News!");
        news.setSummary("In conclusion we can conclude alternative facts are in fact no facts.");
        news.setContent("Today we discovered, that I am to lazy to write more content.");
        news.setPublishedAt(LocalDateTime.of(2002, 1, 13, 12, 55, 59));
        news.setAuthor("Doris Duftler");
        news.setPicturePath(null);
        news.setReadByUsers(new HashSet<>());
        return news;
    }

    public static Location getLocation() {
        final Location location = new Location();
        location.setDescription("Veranstaltunsort");
        location.setAddress(getAddress());
        location.setSeatmap(getSeatmap());
        return location;
    }

    public static Event getEvent() {
        Event event = new Event();
        event.setId(1L);
        event.setTitle("Event-Title");
        event.setCategory(CategoryEnum.FESTIVAL);
        event.setDescription("description");
        event.setDuration(10L);
        event.setArtist(getArtist());
        return event;
    }

    public static Performance getPerformance() {
        Performance performance = new Performance();
        performance.setId(1L);
        performance.setDateTime(OffsetDateTime.now().plusDays(1));
        performance.setLocation(getLocation());
        performance.setEvent(getEvent());
        return performance;
    }

    public static Artist getArtist() {
        Artist artist = new Artist();
        artist.setFirstname("Wolfgang");
        artist.setLastname("Mozart");
        artist.setId(1L);
        return artist;
    }

    public static Booking getBooking() {
        Booking booking = new Booking();
        booking.setId(1L);
        return booking;
    }

}
