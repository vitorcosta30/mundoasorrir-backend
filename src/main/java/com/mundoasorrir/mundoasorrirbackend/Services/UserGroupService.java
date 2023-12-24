package com.mundoasorrir.mundoasorrirbackend.Services;

import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import com.mundoasorrir.mundoasorrirbackend.Domain.UserGroup.UserGroup;
import com.mundoasorrir.mundoasorrirbackend.Repositories.UserGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserGroupService {

    @Autowired
    private final UserGroupRepository userGroupRepository;
    public UserGroup findByGroupId(Long groupId){
        return this.userGroupRepository.getReferenceById(groupId);
    }
    public UserGroup getByGroupId(Long groupId){
        return this.userGroupRepository.getUserGroupByGroupIdEquals(groupId);
    }
    public Boolean existsByGroupName(String groupName){
        return this.userGroupRepository.existsByGroupName(groupName);
    }


    public UserGroup save(UserGroup userGroup){
        return this.userGroupRepository.save(userGroup);
    }
    public List<UserGroup> findUserGroups(String username ){
        return this.userGroupRepository.findByGroupUsers_Username(username);
    }
    public List<UserGroup> findAll(){
        return this.userGroupRepository.findAll();
    }

    public List<SystemUser> getUsersInGroup(Long groupId){
        return this.userGroupRepository.getUsersInGroup(groupId);
    }
    public UserGroup removeUser(SystemUser user, Long groupId){
        UserGroup group = getByGroupId(groupId);
        group.removeUser(user);
        return save(group);
    }
    public UserGroup addUser(SystemUser user, Long groupId){
        UserGroup group = getByGroupId(groupId);
        group.addUser(user);
        return save(group);
    }

    public Boolean isUserCreator(SystemUser user, Long groupId){
        return findByGroupId(groupId).isUserCreator(user);
    }

    public Boolean isUserInGroup(SystemUser user, Long groupId){
        return findByGroupId(groupId).isUserInGroup(user);
    }

}
