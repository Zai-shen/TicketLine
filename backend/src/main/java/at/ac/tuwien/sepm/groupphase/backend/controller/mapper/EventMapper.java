package at.ac.tuwien.sepm.groupphase.backend.controller.mapper;

import at.ac.tuwien.sepm.groupphase.backend.dto.EventCategory;
import at.ac.tuwien.sepm.groupphase.backend.dto.EventDTO;
import at.ac.tuwien.sepm.groupphase.backend.dto.SearchEventDTO;
import at.ac.tuwien.sepm.groupphase.backend.entity.CategoryEnum;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = ArtistMapper.class,injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface EventMapper {
    EventDTO toDto(Event event);

    List<EventDTO> toDto(List<Event> events);

    Event fromDto(EventDTO eventDTO);

    @Mapping(target = "id", ignore = true)
    Event fromSearchDto(SearchEventDTO dto);

    CategoryEnum fromDto(EventCategory category);
}
