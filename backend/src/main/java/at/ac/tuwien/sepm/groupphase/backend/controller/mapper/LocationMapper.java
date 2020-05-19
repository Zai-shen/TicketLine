package at.ac.tuwien.sepm.groupphase.backend.controller.mapper;

import at.ac.tuwien.sepm.groupphase.backend.dto.LocationDTO;
import at.ac.tuwien.sepm.groupphase.backend.dto.SearchLocationDTO;
import at.ac.tuwien.sepm.groupphase.backend.entity.Location;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface LocationMapper {

    Location locationDtoToLocation(LocationDTO locationDTO);

    Location searchLocationDtoToLocation(SearchLocationDTO searchLocationDTO);

    LocationDTO locationToLocationDto(Location location);

    List<LocationDTO> locationsToLocationsDto(List<Location> locations);

}
