package com.mundoasorrir.mundoasorrirbackend.Domain.Vacation;

import com.mundoasorrir.mundoasorrirbackend.Domain.User.Role;

public class BaseStatus {
    /**
     * Coordenador
     */
    public static final Status PENDING = Status.valueOf("PENDING");
    /**
     * Colaborador
     */
    public static final Status ACCEPTED = Status.valueOf("ACCEPTED");
    /**
     * Diretor
     */


    public static final Status REJECTED = Status.valueOf("REJECTED");


    /**
     * get available role types for adding new users
     *
     * @return
     */
    public static Status[] status() {
        return new Status[] {PENDING, ACCEPTED, REJECTED };
    }
}
