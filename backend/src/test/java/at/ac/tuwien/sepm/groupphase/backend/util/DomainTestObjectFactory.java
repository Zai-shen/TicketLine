package at.ac.tuwien.sepm.groupphase.backend.util;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.security.AuthorizationRole;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

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
    public static Location getLocation() {
        final Location location = new Location();
        location.setDescription("Veranstaltunsort");
        location.setAddress(getAddress());
        return location;
    }

    public static Event getEvent() {
        Event event = new Event();
        event.setId(1L);
        event.setTitle("Event-Title");
        event.setCategory(CategoryEnum.FESTIVAL);
        event.setDescription("description");
        event.setDuration(10L);
        return event;
    }

    public static Performance getPerformance() {
        Performance performance = new Performance();
        performance.setId(1L);
        performance.setDateTime(OffsetDateTime.now().plusDays(1));
        performance.setLocation(getLocation());;
        performance.setEvent(getEvent());
        return performance;
    }

}
