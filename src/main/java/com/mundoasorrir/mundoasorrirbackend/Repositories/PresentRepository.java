package com.mundoasorrir.mundoasorrirbackend.Repositories;

import com.mundoasorrir.mundoasorrirbackend.Domain.Attendance.Attendance;
import com.mundoasorrir.mundoasorrirbackend.Domain.Attendance.PresenceStatus;
import com.mundoasorrir.mundoasorrirbackend.Domain.Attendance.Present;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PresentRepository extends JpaRepository<Present, Long> {

    List<Present> findByPresenceStatusEquals(PresenceStatus status);
}
