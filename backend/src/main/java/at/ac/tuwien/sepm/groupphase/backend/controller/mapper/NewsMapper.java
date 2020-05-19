package at.ac.tuwien.sepm.groupphase.backend.controller.mapper;

import at.ac.tuwien.sepm.groupphase.backend.dto.NewsDTO;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Mapper
public interface NewsMapper {

    News toEntity(NewsDTO newsDTO);

    NewsDTO toDTO(News news);

    List<NewsDTO> toDtoList(List<News> news);

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
