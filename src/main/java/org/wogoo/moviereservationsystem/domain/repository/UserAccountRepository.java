package org.wogoo.moviereservationsystem.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wogoo.moviereservationsystem.domain.model.UserAccount;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
