package at.ac.tuwien.sepm.groupphase.backend.controller.mapper;

import at.ac.tuwien.sepm.groupphase.backend.dto.NewsDTO;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Mapper
public interface NewsMapper {

    @Mapping(target = "publishedAt", source = "created")
    @Mapping(target = "text", source = "content")
    News toEntity(NewsDTO newsDTO);

    @Mapping(target = "created", source = "publishedAt")
    @Mapping(target = "content", source = "text")
    NewsDTO toDTO(News news);

    /**
     * Standard mapper for all OffsetDateTimes
     * @param odt the OffsetDateTime
     * @return the converted LocalDateTime
     * */
    default LocalDateTime map(OffsetDateTime odt) {
        return odt == null ? null : odt.toLocalDateTime();
    }

    /**
     * Standard mapper for all LocalDateTimes
     * @param ldt the LocalDateTime
     * @return the converted OffsetDateTime
     * */
    default OffsetDateTime map(LocalDateTime ldt) {
        return ldt == null ? null : ldt.atOffset(OffsetDateTime.now().getOffset());
    }
}
