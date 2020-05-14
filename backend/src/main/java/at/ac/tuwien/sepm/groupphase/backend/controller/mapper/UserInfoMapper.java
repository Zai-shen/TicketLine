package at.ac.tuwien.sepm.groupphase.backend.controller.mapper;

import at.ac.tuwien.sepm.groupphase.backend.dto.UserInfoDTO;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserInfoMapper {

    UserInfoDTO toDto(User user);

    List<UserInfoDTO> toDto(List<User> users);
}
