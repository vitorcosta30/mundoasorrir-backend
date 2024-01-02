package com.mundoasorrir.mundoasorrirbackend.Repositories;

import com.mundoasorrir.mundoasorrirbackend.Domain.Vacation.BaseStatus;
import com.mundoasorrir.mundoasorrirbackend.Domain.Vacation.Status;
import com.mundoasorrir.mundoasorrirbackend.Domain.Vacation.VacationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface VacationRepository extends JpaRepository<VacationRequest, Long> {
    Status accepted = BaseStatus.ACCEPTED;

    VacationRequest getVacationByVacationId(Long id);

    List<VacationRequest> findVacationsByRequestStatusEquals(Status status);

    @Query("select v from VacationRequest v where v.startDate < :obsDate and  v.endDate >= :obsDate and v.requestStatus = :reqStatus")
    List<VacationRequest> activeVacations(Date obsDate, Status reqStatus);

    //List<Vacation> findAllWithEndDateBeforeAndWithStartDateAfter(Date date);
}
