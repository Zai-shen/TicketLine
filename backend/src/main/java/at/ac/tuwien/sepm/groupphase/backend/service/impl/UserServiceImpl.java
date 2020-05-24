package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.exception.DuplicateEntityException;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.security.AuthorizationRole;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import at.ac.tuwien.sepm.groupphase.backend.service.validator.NewUserValidator;
import at.ac.tuwien.sepm.groupphase.backend.service.validator.PasswordValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        LOGGER.debug("Load all user by email");
        try {
            User user = findUserByEmail(email);

            List<GrantedAuthority> grantedAuthorities =
                AuthorityUtils.createAuthorityList(user.getRole().getAuthorities());

            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                grantedAuthorities);
        } catch (NotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage(), e);
        }
    }

    @Override
    public void register(User user) {
        LOGGER.debug("Register user");
        new NewUserValidator().build(user).validate();
        if (userRepository.findUserByEmail(user.getEmail()) != null) {
            throw new DuplicateEntityException("Es existiert bereits ein Nutzer mit dieser Mailadresse");
        }
        user.setRole(AuthorizationRole.USER);
        user.setLocked(false);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void resetPassword(Long userId, String password) {
        LOGGER.debug("Reset Password");
        new PasswordValidator().build(password).validate();
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new NotFoundException("Es existiert kein Nutzer mit der ID " + userId);
        }
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable, String email) {
        LOGGER.debug("Get all Users with page {}, size {} and email {}", pageable.getPageNumber(),
            pageable.getPageSize(), email);

        return email == null ? userRepository.findAll(pageable) :
            userRepository.findAllByEmailContainingIgnoreCase(pageable, email);
    }

    @Override
    public Page<User> getLockedUsers(Pageable pageable, String email) {
        LOGGER.debug("Get all locked Users with page {}, size {} and email {}", pageable.getPageNumber(),
            pageable.getPageSize(), email);

        if (email == null) {
            return userRepository.findAllByLockedIsTrue(pageable);
        } else {
            return userRepository.findAllByEmailContainingIgnoreCaseAndLockedIsTrue(pageable, email);
        }
    }

    @Override
    public void unlockUser(Long userId) {
        LOGGER.debug("Unlock user with id " + userId);
        User user = userRepository.findUserById(userId);
        user.setWrongAttempts(0);
        user.setLocked(false);
        userRepository.save(user);
    }

    private User findUserByEmail(String email) {
        LOGGER.debug("Find user by email");
        User user = userRepository.findUserByEmail(email);
        if (user != null) {
            return user;
        }
        throw new NotFoundException(String.format("Could not find the user with the email address %s", email));
    }
}
