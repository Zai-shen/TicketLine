package at.ac.tuwien.sepm.groupphase.backend.controller;

import at.ac.tuwien.sepm.groupphase.backend.api.UserApi;
import at.ac.tuwien.sepm.groupphase.backend.controller.mapper.UserInfoMapper;
import at.ac.tuwien.sepm.groupphase.backend.controller.mapper.UserMapper;
import at.ac.tuwien.sepm.groupphase.backend.dto.LoginDTO;
import at.ac.tuwien.sepm.groupphase.backend.dto.UserDTO;
import at.ac.tuwien.sepm.groupphase.backend.dto.UserInfoDTO;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.security.AuthorizationRole;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController implements UserApi {

    private static final int USER_LIST_PAGE_SIZE = 25;

    private final UserMapper userMapper;
    private final UserInfoMapper userInfoMapper;
    private final UserService userService;

    @Autowired
    public UserController(UserMapper userMapper, UserInfoMapper userInfoMapper, UserService userService) {
        this.userMapper = userMapper;
        this.userInfoMapper = userInfoMapper;
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
    @Secured(AuthorizationRole.ADMIN_ROLE)
    public ResponseEntity<List<UserInfoDTO>> getUsers(@Valid Optional<Boolean> locked, @Valid Optional<String> email,
        @Valid Optional<Integer> page) {
        // TODO: 15.05.2020 use E-Mail to filter
        PageRequest pageRequest = PageRequest.of(page.orElse(0), USER_LIST_PAGE_SIZE);
        boolean showLockedUsers = locked.orElse(false);
        Page<User> users;
        if (showLockedUsers) {
            users = userService.getLockedUsers(pageRequest);
        } else {
            users = userService.getAllUsers(pageRequest);
        }

        return ResponseEntity.ok(userInfoMapper.toDto(users.getContent()));
    }
}
