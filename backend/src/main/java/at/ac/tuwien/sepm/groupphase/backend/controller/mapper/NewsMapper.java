package at.ac.tuwien.sepm.groupphase.backend.controller.mapper;

import at.ac.tuwien.sepm.groupphase.backend.dto.NewsDTO;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;

public interface NewsMapper {

    News toEntity(NewsDTO newsDTO);

    NewsDTO toDTO(News news);
}
