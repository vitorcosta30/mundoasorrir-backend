package com.mundoasorrir.mundoasorrirbackend.DTO.Vacation;

public class VacationDTO {

    private String id;
    private String startDate;
    private String endDate;
    private String userName;
    private String userEmail;


    public VacationDTO(String id,String startDate, String endDate, String userName, String userEmail) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
