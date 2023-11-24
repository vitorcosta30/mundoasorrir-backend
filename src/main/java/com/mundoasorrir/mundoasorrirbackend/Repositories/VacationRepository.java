package com.mundoasorrir.mundoasorrirbackend.Repositories;

import com.mundoasorrir.mundoasorrirbackend.Domain.Event.Event;
import com.mundoasorrir.mundoasorrirbackend.Domain.Vacation.BaseStatus;
import com.mundoasorrir.mundoasorrirbackend.Domain.Vacation.Status;
import com.mundoasorrir.mundoasorrirbackend.Domain.Vacation.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface VacationRepository extends JpaRepository<Vacation, Long> {
    Status accepted = BaseStatus.ACCEPTED;

    Vacation getVacationByVacationId(Long id);

    List<Vacation> findVacationsByRequestStatusEquals(Status status);

    @Query("select v from Vacation v where v.startDate < :obsDate and  v.endDate >= :obsDate and v.requestStatus = :reqStatus")
    List<Vacation> activeVacations(Date obsDate, Status reqStatus);

    //List<Vacation> findAllWithEndDateBeforeAndWithStartDateAfter(Date date);
}
