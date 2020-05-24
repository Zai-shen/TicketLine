package at.ac.tuwien.sepm.groupphase.backend.security;

import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private UserRepository userRepository;

    public AuthenticationEventListener(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @EventListener
    public void authenticationFailed(AuthenticationFailureBadCredentialsEvent event) {

        String email = (String) event.getAuthentication().getPrincipal();
        LOGGER.debug("unsuccessfull login from  " + email);

        User user = userRepository.findUserByEmail(email);
        if (user != null) {
            user.setWrongAttempts(user.getWrongAttempts() + 1);
            if (user.getWrongAttempts() == 5) {
                user.setLocked(true);
            }
            userRepository.save(user);
        }
    }
}
