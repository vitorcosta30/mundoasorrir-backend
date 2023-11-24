package com.mundoasorrir.mundoasorrirbackend.Services;

import com.mundoasorrir.mundoasorrirbackend.Domain.UserGroup.UserGroup;
import com.mundoasorrir.mundoasorrirbackend.Repositories.FileRepository;
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
    public UserGroup save(UserGroup userGroup){
        return this.userGroupRepository.save(userGroup);
    }
    public List<UserGroup> findUserGroups(String username ){
        return this.userGroupRepository.findByGroupUsers_Username(username);
    }
    public List<UserGroup> findAll(){
        return this.userGroupRepository.findAll();

    }
}
