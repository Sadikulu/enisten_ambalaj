package com.kss.repository;

import com.kss.domains.Role;
import com.kss.domains.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Byte> {

    Optional<Role> findByRoleName(RoleType roleName);

}
