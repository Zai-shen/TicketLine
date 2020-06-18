package at.ac.tuwien.sepm.groupphase.backend.controller.mapper;

import at.ac.tuwien.sepm.groupphase.backend.dto.*;
import at.ac.tuwien.sepm.groupphase.backend.entity.Location;
import at.ac.tuwien.sepm.groupphase.backend.entity.Seatmap;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface LocationMapper {

    Location locationCreationDtoToLocation(LocationCreationDTO locationCreationDTO);

    Location searchLocationDtoToLocation(SearchLocationDTO searchLocationDTO);

    LocationDTO locationToLocationDto(Location location);

    List<LocationDTO> locationsToLocationsDto(List<Location> locations);

    Seatmap fromDto(SeatmapDTO seatmapDTO);
}
