package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.exception.BusinessValidationException;
import at.ac.tuwien.sepm.groupphase.backend.exception.DuplicateEntityException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {

    /**
     * Find a user in the context of Spring Security based on the email address
     * <p>
     * For more information have a look at this tutorial: https://www.baeldung.com/spring-security-authentication-with-a-database
     *
     * @param email the email address
     * @return a Spring Security user
     * @throws UsernameNotFoundException is thrown if the specified user does not exists
     */
    @Override
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

    /**
     * Registers a new user in the database
     *
     * @param user new user to create
     * @throws BusinessValidationException if the user doesn't comply to business validation
     * @throws DuplicateEntityException    if there is already a user with the same email address in the database
     */
    void register(User user);

    /**
     * Reset the password of a user
     *
     * @param userId   id of the user to update
     * @param password to set the user's password to
     * @throws BusinessValidationException if the password doesn't comply to business validation
     */
    void resetPassword(Long userId, String password);

    /**
     * @param pageable Pagination information
     * @param email Search for email if not null
     * @return returns a list with all Users. Restricted by the pagination.
     */
    Page<User> getAllUsers(Pageable pageable, @Nullable String email);

    /**
     * @param pageable Pagination information
     * @param email Search for email if not null
     * @return returns a list with all Users. Restricted by the pagination.
     */
    Page<User> getLockedUsers(Pageable pageable, @Nullable String email);

    /**
     * Unlock user and reset attempts
     * @param userId id of the user to unlock
     */
    void unlockUser(Long userId);

    /**
     * Lock user independently of wrong attempts
     * @param userId id of the user to lock
     */
    void lockUser(Long userId);
}
