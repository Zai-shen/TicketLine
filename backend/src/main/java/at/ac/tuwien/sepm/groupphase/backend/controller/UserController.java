package at.ac.tuwien.sepm.groupphase.backend.controller;

import at.ac.tuwien.sepm.groupphase.backend.api.UserApi;
import at.ac.tuwien.sepm.groupphase.backend.controller.mapper.AddressMapper;
import at.ac.tuwien.sepm.groupphase.backend.controller.mapper.UserMapper;
import at.ac.tuwien.sepm.groupphase.backend.dto.LoginDTO;
import at.ac.tuwien.sepm.groupphase.backend.dto.UserDTO;
import at.ac.tuwien.sepm.groupphase.backend.dto.UserUpdateDTO;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.security.AuthorizationRole;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;

@Controller
public class UserController implements UserApi {

    private final UserMapper userMapper;
    private final AddressMapper addressMapper;
    private final UserService userService;

    @Autowired
    public UserController(UserMapper userMapper, AddressMapper addressMapper, UserService userService) {
        this.userMapper = userMapper;
        this.addressMapper = addressMapper;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<Void> register(@Valid UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        userService.register(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    @Secured(AuthorizationRole.ADMIN_ROLE)
    public ResponseEntity<Void> resetPassword(Long userId, @Valid LoginDTO loginDTO) {
        userService.resetPassword(userId, loginDTO.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @Secured(AuthorizationRole.USER_ROLE)
    public ResponseEntity<Void> updateUser(Long userId, @Valid UserUpdateDTO userUpdateDTO) {
        String username =  (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userService.findUserByEmail(username);
        if(!userId.equals(currentUser.getId()) && currentUser.getRole() != AuthorizationRole.ADMIN) {
            throw new AccessDeniedException("You may only update your own user");
        }
        if(userUpdateDTO.getFirstname() != null && !userUpdateDTO.getFirstname().isEmpty())
            currentUser.setFirstname(userUpdateDTO.getFirstname());
        if(userUpdateDTO.getLastname() != null && !userUpdateDTO.getLastname().isEmpty())
            currentUser.setLastname(userUpdateDTO.getLastname());
        long addressId = currentUser.getAddress().getId();
        if(userUpdateDTO.getAddress() != null) {
            currentUser.setAddress(addressMapper.fromDto(userUpdateDTO.getAddress()));
            currentUser.getAddress().setId(addressId);
        }
        userService.updateUser(currentUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @Secured(AuthorizationRole.USER_ROLE)
    public ResponseEntity<UserDTO> getSelf() {
        String username =  (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userService.findUserByEmail(username);
        return ResponseEntity.ok(userMapper.toDto(currentUser));
    }
}
