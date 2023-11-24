package com.mundoasorrir.mundoasorrirbackend.Services;

import com.mundoasorrir.mundoasorrirbackend.Domain.Attendance.Attendance;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import com.mundoasorrir.mundoasorrirbackend.Repositories.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    @Autowired
    private final AttendanceRepository attendanceRepository;

    public boolean isInstantiated(Date date){
        return this.attendanceRepository.findByDayAttendanceEquals(date).isPresent();
    }

    public Attendance save(Date date, List<SystemUser> users){
        return this.attendanceRepository.save(new Attendance(date,users));

    }
    public Attendance save(Attendance attendance){
        return this.attendanceRepository.save(attendance);

    }

    public List<SystemUser> getAttendanceUnmarkedByDate(Date date){
        return this.attendanceRepository.findByDayAttendanceEquals(date).get().getUsersUnmarked();
    }

    public Attendance markAsPresent(Date date, SystemUser user){
        if(this.attendanceRepository.findByDayAttendanceEquals(date).isPresent()) {
            Attendance attendance = this.attendanceRepository.findByDayAttendanceEquals(date).get();
            attendance.markPresent(user);
            return save(attendance);
        }else{
            return null;
        }
    }
    public Attendance markAsAbsent(Date date, SystemUser user){
        if(this.attendanceRepository.findByDayAttendanceEquals(date).isPresent()) {
            Attendance attendance = this.attendanceRepository.findByDayAttendanceEquals(date).get();
            attendance.markAbsent(user);
            return save(attendance);
        }else{
            return null;
        }
    }

}
