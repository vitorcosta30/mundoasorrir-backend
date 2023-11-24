package com.mundoasorrir.mundoasorrirbackend.Domain.Attendance;

public class PresenceStatus {

    private String status;

    private PresenceStatus(String status) {
        this.status = status;
    }

    public PresenceStatus() {
        this.status = unmarked();
    }


    public static PresenceStatus valueOf(String status){return new PresenceStatus(status);}

    public void wasPresent(){
        if(this.status.equals(unmarked())) {
            this.status = present();
        }else{
            throw new IllegalCallerException("Not allowed");
        }



    }

    public void wasAbsent(){
        if(this.status.equals(unmarked())) {
            this.status = absent();
        }else{
            throw new IllegalCallerException("Not allowed");
        }    }




    private String unmarked(){
        return BasePresenceStatus.UNMARKED.getStatus();
    }
    private String present(){
        return BasePresenceStatus.PRESENT.getStatus();
    }

    private String absent(){
        return BasePresenceStatus.ABSENT.getStatus();
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
