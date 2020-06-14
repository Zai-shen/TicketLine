package at.ac.tuwien.sepm.groupphase.backend.util;

import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.entity.Location;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.security.AuthorizationRole;

import java.time.LocalDateTime;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;

import java.time.OffsetDateTime;
import java.util.Collections;

public class DomainTestObjectFactory {

    public static User getUser() {
        final User user = new User();
        user.setAddress(getAddress());
        user.setRole(AuthorizationRole.USER);
        user.setFirstname("Hans");
        user.setLastname("Müller");
        user.setEmail("hans.mueller@example.com");
        user.setPassword("secretPassword");
        user.setId(14L);
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
        final Seat seat = new Seat();
            seat.setColLabel("colLabel");
            seat.setRowLabel("rowlabel");
            seat.setPrice(10.0);
            seat.setRadius(5.0);
            seat.setX(10.0);
            seat.setY(10.0);
        final SeatLabel seatLabel = new SeatLabel();
            seatLabel.setX(0.0);
            seatLabel.setY(0.0);
            seatLabel.setSize(5.0);
            seatLabel.setText("test");
        final StandingArea standingArea = new StandingArea();
            standingArea.setMaxPeople(10L);
            standingArea.setName("standingArea");
            standingArea.setPrice(10.0);
            standingArea.setX(10.0);
            standingArea.setY(10.0);
            standingArea.setWidth(10.0);
            standingArea.setHeight(10.0);
        final SeatGroupArea seatGroupArea = new SeatGroupArea();
            seatGroupArea.setName("seatArea");
            seatGroupArea.setSeatLabels(Collections.singleton(seatLabel));
            seatGroupArea.setSeats(Collections.singleton(seat));
            seatGroupArea.setX(10.0);
            seatGroupArea.setY(10.0);
            seatGroupArea.setWidth(10.0);
            seatGroupArea.setHeight(10.0);
        final Seatmap seatmap = new Seatmap();
            seatmap.setSeatGroupAreas(Collections.singleton(seatGroupArea));
            seatmap.setStandingAreas(Collections.singleton(standingArea));
        return seatmap;
    }

    public static News getNews() {
        final News news = new News();
        news.setId(16L);
        news.setTitle("Breaking News!");
        news.setSummary("In conclusion we can conclude alternative facts are in fact no facts.");
        news.setContent("Today we discovered, that I am to lazy to write more content.");
        news.setPublishedAt(LocalDateTime.of(2002, 1, 13, 12, 55, 59));
        news.setAuthor("Doris Duftler");
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
        ;
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

}
