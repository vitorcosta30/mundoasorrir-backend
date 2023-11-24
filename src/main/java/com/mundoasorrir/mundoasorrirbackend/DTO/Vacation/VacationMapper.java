package com.mundoasorrir.mundoasorrirbackend.DTO.Vacation;

import com.mundoasorrir.mundoasorrirbackend.Domain.Vacation.Vacation;

import java.util.ArrayList;
import java.util.List;

public class VacationMapper {


    public static VacationDTO toDTO(Vacation vacation){
        String username = vacation.getUser().getUsername();
        String email = vacation.getUser().getEmail();
        String startDate = vacation.getStartDate().toString();
        String endDate = vacation.getEndDate().toString();
        return new VacationDTO(vacation.getVacationId().toString(),startDate,endDate,username,email);
    }
    public static List<VacationDTO> toDTO(List<Vacation> vacations){
        List<VacationDTO> res = new ArrayList<>();
        for(int i = 0; i < vacations.size(); i++){
            res.add(toDTO(vacations.get(i)));
        }
        return res;


    }

}
