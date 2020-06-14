package at.ac.tuwien.sepm.groupphase.backend.controller.mapper;

import at.ac.tuwien.sepm.groupphase.backend.dto.ArtistDTO;
import at.ac.tuwien.sepm.groupphase.backend.dto.SearchArtistDTO;
import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ArtistMapper {
    ArtistDTO toDto(Artist artist);

    List<ArtistDTO> toDto(List<Artist> artists);

    Artist fromDto(SearchArtistDTO artistDTO);

    Artist fromDto(ArtistDTO artistDTO);

}
