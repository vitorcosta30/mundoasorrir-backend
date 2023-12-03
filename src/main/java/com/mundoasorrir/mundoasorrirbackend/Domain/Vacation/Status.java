package com.mundoasorrir.mundoasorrirbackend.Domain.Vacation;

import lombok.Getter;

public class Status {

    @Getter
    public String currentStatus;


    public Status(){
        this.currentStatus = pending();
    }

    private Status(String status){
        this.currentStatus = status;
    }


    public static Status valueOf(String status){
        return new Status(status);
    }

    public void accept(){
        if(this.currentStatus.equals(pending())){
            this.currentStatus = this.accepted();
        }else{
            throw new IllegalCallerException("Not allowed");
        }
    }


    public void reject(){
        if(this.currentStatus.equals(pending())){
            this.currentStatus = rejected();
        }else{
            throw new IllegalCallerException("Not allowed");
        }

    }
    private String pending(){
        return BaseStatus.PENDING.currentStatus;
    }
    private String rejected(){
        return BaseStatus.REJECTED.currentStatus;
    }
    private String accepted(){
        return BaseStatus.ACCEPTED.currentStatus;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Status otherStatus){
            return this.currentStatus.equalsIgnoreCase(otherStatus.getCurrentStatus());

        }else{
            return false;
        }
    }
}
