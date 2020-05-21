package at.ac.tuwien.sepm.groupphase.backend.controller.mapper;

import at.ac.tuwien.sepm.groupphase.backend.dto.AddressDTO;
import at.ac.tuwien.sepm.groupphase.backend.dto.LocationDTO;
import at.ac.tuwien.sepm.groupphase.backend.dto.SearchLocationDTO;
import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import at.ac.tuwien.sepm.groupphase.backend.entity.Location;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface AddressMapper {
    Address fromDto(AddressDTO addressDTO);
}
