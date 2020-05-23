package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.exception.DuplicateEntityException;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.security.AuthorizationRole;
import at.ac.tuwien.sepm.groupphase.backend.service.impl.UserServiceImpl;
import at.ac.tuwien.sepm.groupphase.backend.util.DomainTestObjectFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testRegisterUserAlreadyExisting() {
        when(userRepository.findUserByEmail(any())).thenReturn(new User());

        assertThatThrownBy(() -> userService.register(DomainTestObjectFactory.getUser())).isExactlyInstanceOf(
            DuplicateEntityException.class);
        verify(userRepository, never()).save(any());
    }

    @Test
    public void testRegisterUserWithValidInput() {
        when(userRepository.findUserByEmail(any())).thenReturn(null);
        when(passwordEncoder.encode(any())).thenReturn("encoded");
        final User user = DomainTestObjectFactory.getUser();

        userService.register(user);

        assertThat(user.getRole()).isEqualTo(AuthorizationRole.USER);
        assertThat(user.getPassword()).isEqualTo("encoded");
        assertThat(user.getLocked()).isFalse();
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testResetPasswordWithValidInput() {
        final User user = new User();
        when(userRepository.findUserById(any())).thenReturn(user);
        when(passwordEncoder.encode(any())).thenReturn("encoded");

        userService.resetPassword(4L, "myPassword");

        assertThat(user.getPassword()).isEqualTo("encoded");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testResetPasswordWithUserNotExisting() {
        when(userRepository.findUserById(any())).thenReturn(null);

        assertThatThrownBy(() -> userService.resetPassword(4L, "myPassword")).isExactlyInstanceOf(
            NotFoundException.class);
        verify(userRepository, never()).save(any());
    }

    @Test
    public void testGetAllUsersWithoutEmail() {
        Pageable pageable = PageRequest.of(0, 20);
        userService.getAllUsers(pageable, null);

        verify(userRepository, times(1)).findAll(pageable);
    }

    @Test
    public void testGetAllUsersWithEmail() {
        Pageable pageable = PageRequest.of(0, 20);
        String searchEmail = "test@example.com";
        userService.getAllUsers(pageable, searchEmail);

        verify(userRepository, times(1)).findAllByEmailContainingIgnoreCase(pageable, searchEmail);
    }

    @Test
    public void testGetLockedUsersWithoutEmail() {
        Pageable pageable = PageRequest.of(0, 20);
        userService.getLockedUsers(pageable, null);

        verify(userRepository, times(1)).findAllByLockedIsTrue(pageable);
    }

    @Test
    public void testGetLockedUsersWithEmail() {
        Pageable pageable = PageRequest.of(0, 20);
        String searchEmail = "test@example.com";
        userService.getLockedUsers(pageable, searchEmail);

        verify(userRepository, times(1)).findAllByEmailContainingIgnoreCaseAndLockedIsTrue(pageable, searchEmail);
    }
}
