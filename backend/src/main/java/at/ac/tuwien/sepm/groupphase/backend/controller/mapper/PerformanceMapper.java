package at.ac.tuwien.sepm.groupphase.backend.controller.mapper;

import at.ac.tuwien.sepm.groupphase.backend.dto.PerformanceDTO;
import at.ac.tuwien.sepm.groupphase.backend.dto.SearchPerformanceDTO;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = EventMapper.class)
public interface PerformanceMapper {
    PerformanceDTO toDto(Performance event);

    List<PerformanceDTO> toDto(List<Performance> events);

    Performance fromDto(PerformanceDTO eventDTO);

    Performance fromDto(SearchPerformanceDTO performanceDTO);
}
