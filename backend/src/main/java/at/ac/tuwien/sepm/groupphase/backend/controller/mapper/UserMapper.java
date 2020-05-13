package at.ac.tuwien.sepm.groupphase.backend.controller.mapper;

import at.ac.tuwien.sepm.groupphase.backend.dto.UserDTO;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.security.AuthorizationRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(imports = AuthorizationRole.class)
public interface UserMapper {

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "email", source = "login.email")
    @Mapping(target = "password", source = "login.password")
    User toEntity(UserDTO userDTO);
}
