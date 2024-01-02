package com.mundoasorrir.mundoasorrirbackend.Domain.Vacation;

import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;
@Entity
public class VacationRequest {

    @Getter
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long vacationId;

    public Date startDate;

    public Date endDate;

    @ManyToOne
    public SystemUser user;


    @Embedded
    public Status requestStatus;

    public VacationRequest(Date startDate, Date endDate, SystemUser user) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
        this.requestStatus = new Status();
    }

    public VacationRequest() {

    }

    public void acceptRequest(){
        this.requestStatus.accept();

    }
    public void rejectRequest(){
        this.requestStatus.reject();

    }

    public Long getVacationId() {
        return vacationId;
    }

    public void setVacationId(Long vacationId) {
        this.vacationId = vacationId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public SystemUser getUser() {
        return user;
    }

    public void setUser(SystemUser user) {
        this.user = user;
    }

    public Status getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(Status requestStatus) {
        this.requestStatus = requestStatus;
    }
}
