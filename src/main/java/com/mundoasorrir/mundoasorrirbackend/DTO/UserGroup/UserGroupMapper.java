package com.mundoasorrir.mundoasorrirbackend.DTO.UserGroup;

import com.mundoasorrir.mundoasorrirbackend.Domain.UserGroup.UserGroup;

import java.util.ArrayList;
import java.util.List;

public class UserGroupMapper {

    public static UserGroupDTO toDTO(UserGroup userGroup){
        return new UserGroupDTO(userGroup.getGroupId().toString(),userGroup.getGroupDesignation(), userGroup.getGroupName());
    }

    public static List<UserGroupDTO> toDTO(List<UserGroup> userGroups){
        List<UserGroupDTO> res = new ArrayList<>();
        for(int i = 0; i < userGroups.size(); i++ ){
            res.add(toDTO(userGroups.get(i)));

        }
        return res;
    }
}
