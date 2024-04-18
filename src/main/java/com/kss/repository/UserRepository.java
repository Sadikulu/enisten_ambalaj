package com.kss.repository;

import com.kss.domains.User;
import com.kss.domains.enums.RoleType;
import com.kss.domains.enums.UserStatus;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = "roles")
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    @EntityGraph(attributePaths = "roles")
    @NotNull
    List<User> findAll();

    @EntityGraph(attributePaths = {"roles","favoriteList"})
    @NotNull
    Optional<User> findById(@NotNull Long id);

    @EntityGraph(attributePaths = {"id","shoppingCart"})
    Optional<User> findUserById(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE User u " +
            "SET u.status = :status WHERE u.email = :email")
    void enableUser(@Param("status") UserStatus status, @Param("email") String email);

    void deleteAllByBuiltInFalse();

    long count();

    @Query("select u from User u inner join u.roles r where  r.roleName = :role")
    List<User> findByRole(@Param("role") RoleType roleType);
}
