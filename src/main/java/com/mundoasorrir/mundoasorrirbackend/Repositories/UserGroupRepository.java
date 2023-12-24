package com.mundoasorrir.mundoasorrirbackend.Repositories;

import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import com.mundoasorrir.mundoasorrirbackend.Domain.UserGroup.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {

    List<UserGroup> findByGroupUsers_Username(String username);
    Boolean existsByGroupName(String groupname);


    UserGroup getUserGroupByGroupIdEquals(Long groupId);

    @Query("Select g.groupUsers from UserGroup g where g.groupId = :groupId")
    List<SystemUser> getUsersInGroup(Long groupId);
}
