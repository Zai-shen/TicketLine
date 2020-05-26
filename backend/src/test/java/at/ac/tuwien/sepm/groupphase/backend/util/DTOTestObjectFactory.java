package at.ac.tuwien.sepm.groupphase.backend.util;

import at.ac.tuwien.sepm.groupphase.backend.dto.AddressDTO;
import at.ac.tuwien.sepm.groupphase.backend.dto.LoginDTO;
import at.ac.tuwien.sepm.groupphase.backend.dto.UserDTO;
import at.ac.tuwien.sepm.groupphase.backend.dto.UserUpdateDTO;
import at.ac.tuwien.sepm.groupphase.backend.dto.*;

import java.time.OffsetDateTime;

public class DTOTestObjectFactory {

    public static UserDTO getUserDTO() {
        final UserDTO userDTO = new UserDTO();
        userDTO.setId(14L);
        userDTO.setFirstname("Hans");
        userDTO.setLastname("Müller");
        userDTO.setLogin(getLoginDTO());
        userDTO.setAddress(getAddressDTO());
        return userDTO;
    }

    public static UserUpdateDTO getUserUpdateDTO() {
        UserDTO reference = getUserDTO();
        final UserUpdateDTO updateDTO = new UserUpdateDTO();
        updateDTO.setAddress(getAddressDTO());
        updateDTO.setFirstname(reference.getFirstname());
        updateDTO.setLastname(reference.getLastname());
        return updateDTO;
    }

    public static LoginDTO getLoginDTO() {
        final LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("hans.mueller@example.com");
        loginDTO.setPassword("secretPassword");
        return loginDTO;
    }

    public static AddressDTO getAddressDTO() {
        final AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet("Hauptstraße");
        addressDTO.setHousenr("24A");
        addressDTO.setPostalcode("3100");
        addressDTO.setCity("St. Pölten");
        addressDTO.setCountry("Österreich");
        return addressDTO;
    }

    public static LocationDTO getLocationDTO() {
        return new LocationDTO().id(0L).address(getAddressDTO()).description("Location123");
    }

    public static EventDTO getEventDTO() {
        return new EventDTO().title("Event-Title")
            .category(EventCategory.FESTIVAL)
            .description("description")
            .duration(10L);
    }

    public static PerformanceDTO getPerformanceDTO() {
     return new PerformanceDTO().dateTime(OffsetDateTime.now().plusDays(1)).event(getEventDTO()).location(getLocationDTO());
    }
}
