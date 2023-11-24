package com.mundoasorrir.mundoasorrirbackend.Repositories;

import java.util.Optional;

import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<SystemUser, Long> {
    Optional<SystemUser> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);


}