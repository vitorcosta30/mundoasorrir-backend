package com.mundoasorrir.mundoasorrirbackend.Repositories;

import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<SystemUser, Long> {
    Optional<SystemUser> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);




    @Query("Select u.isActive from SystemUser u where u.email = :email")
    Boolean isUserActivateEmail(String email);

    @Query("Select u.isActive from SystemUser u where u.username = :username")
    Boolean isUserActivateUsername(String username);


}