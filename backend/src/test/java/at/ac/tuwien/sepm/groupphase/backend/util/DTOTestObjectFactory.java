package at.ac.tuwien.sepm.groupphase.backend.util;

import at.ac.tuwien.sepm.groupphase.backend.dto.*;

import java.time.LocalDateTime;
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

    public static NewsDTO getNewsDTO(){
        final NewsDTO newsDTO = new NewsDTO();
        newsDTO.setId(16L);
        newsDTO.setTitle("Breaking News!");
        newsDTO.setSummary("In conclusion we can conclude alternative facts are in fact no facts.");
        newsDTO.setContent("Today we discovered, that I am to lazy to write more content.");
        newsDTO.setPublishedAt(LocalDateTime.of(2002,1,13,12,55,59).atOffset(OffsetDateTime.now().getOffset()));
        newsDTO.setAuthor("Doris Duftler");
        return newsDTO;
    }
}
