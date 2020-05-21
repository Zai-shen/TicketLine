package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.security.AuthorizationRole;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;

@Profile("generateData")
@Component
public class UserDataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final int NUMBER_OF_USERS_TO_GENERATE = 20;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDataGenerator(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    private void generateUsers() {
        Faker f = new Faker();
        if (!userRepository.findAll().isEmpty()) {
            LOGGER.debug("users already generated");
        } else {
            LOGGER.debug("generating {} user entries", NUMBER_OF_USERS_TO_GENERATE);
            for (int i = 0; i < NUMBER_OF_USERS_TO_GENERATE; i++) {
                User user = new User();
                user.setEmail(f.name().firstName() + "." + f.name().lastName() + "@example.com");
                user.setPassword(passwordEncoder.encode("12345678"));
                user.setFirstname(f.name().firstName());
                user.setLastname(f.name().lastName());
                user.setLocked(false);
                user.setRole(AuthorizationRole.USER);
                user.setAddress(getAddress(f));

                userRepository.save(user);
            }
            generateAdmins(f);
        }
    }

    private void generateAdmins(Faker f) {
        LOGGER.debug("generating admins");

        User admin = new User();
        admin.setEmail("admin@example.com");
        admin.setPassword(passwordEncoder.encode("87654321"));
        admin.setFirstname("Max");
        admin.setLastname("Master");
        admin.setLocked(false);
        admin.setRole(AuthorizationRole.ADMIN);
        admin.setAddress(getAddress(f));
        userRepository.save(admin);

        User coadmin = new User();
        coadmin.setEmail("coadmin@example.com");
        coadmin.setPassword(passwordEncoder.encode("87654321"));
        coadmin.setFirstname("Ben");
        coadmin.setLastname("Bachelor");
        coadmin.setLocked(false);
        coadmin.setRole(AuthorizationRole.ADMIN);
        coadmin.setAddress(getAddress(f));
        userRepository.save(coadmin);
    }

    private Address getAddress(Faker f) {
        Address useraddr = new Address();
        useraddr.setCity(f.pokemon().location());
        useraddr.setCountry(f.gameOfThrones().city());
        useraddr.setStreet(f.address().streetName());
        useraddr.setHousenr(f.address().buildingNumber());
        useraddr.setPostalcode(f.address().zipCode());
        return useraddr;
    }

}
