package com.vnpt.polling.repository;

import com.vnpt.polling.model.Role;
import com.vnpt.polling.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName roleName);
}
