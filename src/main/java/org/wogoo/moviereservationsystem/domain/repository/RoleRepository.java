package org.wogoo.moviereservationsystem.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wogoo.moviereservationsystem.domain.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
