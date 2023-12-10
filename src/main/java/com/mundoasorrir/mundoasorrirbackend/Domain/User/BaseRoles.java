package com.mundoasorrir.mundoasorrirbackend.Domain.User;

public final class BaseRoles {
    /**
     * Coordenador
     */
    public static final Role MANAGER = Role.valueOf("COORDENADOR");
    /**
     * Colaborador
     */
    public static final Role EMPLOYEE = Role.valueOf("COLABORADOR");
    /**
     * Diretor
     */


    public static final Role DIRECTOR = Role.valueOf("DIRETOR");


    /**
     * get available role types for adding new users
     *
     * @return
     */
    public static Role[] systemRoles() {
        return new Role[] {MANAGER, EMPLOYEE, DIRECTOR };
    }


}