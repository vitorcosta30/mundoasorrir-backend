package com.mundoasorrir.mundoasorrirbackend.Repositories;

import com.mundoasorrir.mundoasorrirbackend.Domain.Attendance.Attendance;
import com.mundoasorrir.mundoasorrirbackend.Domain.Attendance.Present;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Optional<Attendance> findByDayAttendanceEquals(Date date);


    @Query("Select a.userAttendance from Attendance a where a.dayAttendance = :date ")
    List<Present> getPresentStatusList(Date date);


;

}
