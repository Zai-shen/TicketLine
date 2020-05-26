package at.ac.tuwien.sepm.groupphase.backend.basetest;

import at.ac.tuwien.sepm.groupphase.backend.dto.*;
import at.ac.tuwien.sepm.groupphase.backend.entity.CategoryEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public interface TestData {

    Long ID = 1L;
    String TEST_NEWS_TITLE = "Title";
    String TEST_NEWS_SUMMARY = "Summary";
    String TEST_NEWS_TEXT = "TestMessageText";
    LocalDateTime TEST_NEWS_PUBLISHED_AT =
        LocalDateTime.of(2019, 11, 13, 12, 15, 0, 0);

    AddressDTO ADDRESS_DTO = new AddressDTO().city("city").country("country").housenr("123").postalcode("postalCode");
    LocationDTO LOCATION_DTO = new LocationDTO().id(0L).address(ADDRESS_DTO).description("Location123");
    EventDTO EVENT_DTO = new EventDTO().title("Event-Title")
        .category(EventCategory.FESTIVAL)
        .description("description")
        .duration(10L);
    PerformanceDTO PERFORMANCE_DTO = new PerformanceDTO().dateTime(OffsetDateTime.now().plusDays(1)).event(EVENT_DTO).location(LOCATION_DTO);
}
