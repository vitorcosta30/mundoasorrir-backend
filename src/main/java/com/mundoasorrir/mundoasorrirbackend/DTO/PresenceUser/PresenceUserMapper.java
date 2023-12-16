package com.mundoasorrir.mundoasorrirbackend.DTO.PresenceUser;

import com.mundoasorrir.mundoasorrirbackend.DTO.User.UserMapper;
import com.mundoasorrir.mundoasorrirbackend.Domain.Attendance.Present;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PresenceUserMapper {
    public static List<PresenceUserDTO> toDTO(List<Present> presences){
        List<PresenceUserDTO> res = new ArrayList<>();
        for(int i = 0; i  < presences.size(); i++){
            res.add(toDTO(presences.get(i)));
        }
        return res;
    }

    public static PresenceUserDTO toDTO(Present presence){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return new PresenceUserDTO(UserMapper.toDTO(presence.getUser()),presence.getPresenceStatus().getStatus(), dateFormat.format(presence.getAttendance().getDayAttendance()));
    }
}
