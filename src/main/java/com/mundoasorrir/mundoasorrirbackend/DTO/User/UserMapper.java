package com.mundoasorrir.mundoasorrirbackend.DTO.User;

import com.mundoasorrir.mundoasorrirbackend.DTO.Project.ProjectDTO;
import com.mundoasorrir.mundoasorrirbackend.DTO.Project.ProjectMapper;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static UserDTO toDTO(SystemUser user){
        if(user.getCurrentProject() != null){
            return new UserDTO(user.getUserId().toString(),user.getUsername(), user.getEmail(), user.getRole().getName(),user.isActive(), ProjectMapper.toDTO(user.getCurrentProject()));

        }
        return new UserDTO(user.getUserId().toString(),user.getUsername(), user.getEmail(), user.getRole().getName(),user.isActive(), new ProjectDTO());
    }


    public static List<UserDTO> toDTO(List<SystemUser> users){
        List<UserDTO> res = new ArrayList<>();
        for(int i = 0; i < users.size(); i++){
            res.add(toDTO(users.get(i)));
        }
        return res;

    }
}
