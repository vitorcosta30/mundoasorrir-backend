package com.mundoasorrir.mundoasorrirbackend.Repositories;

import com.mundoasorrir.mundoasorrirbackend.Domain.Event.Event;
import com.mundoasorrir.mundoasorrirbackend.Domain.UserGroup.UserGroup;
import com.mundoasorrir.mundoasorrirbackend.Domain.Vacation.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {

    List<UserGroup> findByGroupUsers_Username(String username);
}
