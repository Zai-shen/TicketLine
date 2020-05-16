package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findUserByEmail(String email);

    User findUserById(Long id);

    Page<User> findAllByLockedIsTrue(Pageable pageable);

    Page<User> findAllByEmailContaining(Pageable pageable, String email);

    Page<User> findAllByEmailContainingAndLockedIsTrue(Pageable pageable, String email);
}
