package at.ac.tuwien.sepm.groupphase.backend.controller.mapper;

import at.ac.tuwien.sepm.groupphase.backend.dto.SeatLabelDTO;
import at.ac.tuwien.sepm.groupphase.backend.dto.SeatOccupationDTO;
import at.ac.tuwien.sepm.groupphase.backend.dto.SeatgroupOccupationDTO;
import at.ac.tuwien.sepm.groupphase.backend.dto.StandingAreaOccupationDTO;
import at.ac.tuwien.sepm.groupphase.backend.entity.Seat;
import at.ac.tuwien.sepm.groupphase.backend.entity.SeatGroupArea;
import at.ac.tuwien.sepm.groupphase.backend.entity.SeatLabel;
import at.ac.tuwien.sepm.groupphase.backend.entity.StandingArea;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper
public interface SeatMapper {

    SeatOccupationDTO fromEntity(Seat seat);

    List<SeatLabelDTO> fromEntity(Set<SeatLabel> seat);

    StandingAreaOccupationDTO fromEntity(StandingArea sa);

    SeatgroupOccupationDTO fromEntity(SeatGroupArea sga);
}
