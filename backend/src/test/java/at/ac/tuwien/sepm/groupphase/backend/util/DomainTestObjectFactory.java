package at.ac.tuwien.sepm.groupphase.backend.util;

import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import at.ac.tuwien.sepm.groupphase.backend.entity.Location;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.security.AuthorizationRole;

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
}
