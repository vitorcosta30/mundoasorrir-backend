package com.mundoasorrir.mundoasorrirbackend.DTO.Presence;

import com.mundoasorrir.mundoasorrirbackend.DTO.User.UserMapper;
import com.mundoasorrir.mundoasorrirbackend.Domain.Attendance.Present;

import java.util.ArrayList;
import java.util.List;

public class PresenceMapper {
    public static List<PresenceDTO> toDTO(List<Present> presences){
        List<PresenceDTO> res = new ArrayList<>();
        for(int i = 0; i  < presences.size(); i++){
            res.add(toDTO(presences.get(i)));
        }
        return res;
    }

    public static PresenceDTO toDTO(Present presence){
        return new PresenceDTO(UserMapper.toDTO(presence.getUser()),presence.getPresenceStatus().getStatus());
    }
}
