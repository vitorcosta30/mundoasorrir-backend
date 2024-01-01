package com.mundoasorrir.mundoasorrirbackend.Services;

import com.mundoasorrir.mundoasorrirbackend.Domain.Vacation.BaseStatus;
import com.mundoasorrir.mundoasorrirbackend.Domain.Vacation.Vacation;
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
    public Vacation getReferanceById(Long id){return this.vacationRepository.getReferenceById(id);}

    /**
     *
     * @param id
     * @return
     */


    public Vacation getById(Long id){return this.vacationRepository.getVacationByVacationId(id);}

    /**
     *
     * @param vacation
     * @return
     */

    public Vacation save(Vacation vacation){
        return this.vacationRepository.save(vacation);
    }

    /**
     *
     * @param id
     * @return
     */

    public Vacation acceptVacation(Long id){
        Vacation vacation = this.getById(id);
        vacation.acceptRequest();
        return this.save(vacation);
    }

    /**
     *
     * @param id
     * @return
     */

    public Vacation rejectVacation(Long id){
        Vacation vacation = this.getById(id);
        vacation.rejectRequest();
        return this.save(vacation);
    }

    /**
     *
     * @return
     */

    public List<Vacation> getPendingRequests(){
        return this.vacationRepository.findVacationsByRequestStatusEquals(BaseStatus.PENDING);
    }

    /**
     *
     * @param obsDate
     * @return
     */

    public List<Vacation> getActiveVacations(Date obsDate){
        return this.vacationRepository.activeVacations(obsDate,BaseStatus.ACCEPTED);
    }

}
