package com.mundoasorrir.mundoasorrirbackend.Services;

import com.mundoasorrir.mundoasorrirbackend.Domain.Vacation.BaseStatus;
import com.mundoasorrir.mundoasorrirbackend.Domain.Vacation.VacationRequest;
import com.mundoasorrir.mundoasorrirbackend.Repositories.VacationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacationService {
    @Autowired
    private final VacationRepository vacationRepository;


    /**
     * returns copy of object, used when it comes to listings
     * @param id
     * @return
     */
    public VacationRequest getReferanceById(Long id){return this.vacationRepository.getReferenceById(id);}

    /**
     *
     * @param id
     * @return
     */


    public VacationRequest getById(Long id){return this.vacationRepository.getVacationByVacationId(id);}

    /**
     *
     * @param vacation
     * @return
     */

    public VacationRequest save(VacationRequest vacation){
        return this.vacationRepository.save(vacation);
    }

    /**
     *
     * @param id
     * @return
     */

    public VacationRequest acceptVacation(Long id){
        VacationRequest vacation = this.getById(id);
        vacation.acceptRequest();
        return this.save(vacation);
    }

    /**
     *
     * @param id
     * @return
     */

    public VacationRequest rejectVacation(Long id){
        VacationRequest vacationRequest = this.getById(id);
        vacationRequest.rejectRequest();
        return this.save(vacationRequest);
    }

    /**
     *
     * @return
     */

    public List<VacationRequest> getPendingRequests(){
        return this.vacationRepository.findVacationsByRequestStatusEquals(BaseStatus.PENDING);
    }

    /**
     *
     * @param obsDate
     * @return
     */

    public List<VacationRequest> getActiveVacations(Date obsDate){
        return this.vacationRepository.activeVacations(obsDate,BaseStatus.ACCEPTED);
    }

}
