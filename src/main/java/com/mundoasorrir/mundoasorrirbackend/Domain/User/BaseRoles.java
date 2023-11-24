package com.mundoasorrir.mundoasorrirbackend.Domain.User;

public final class BaseRoles {
    /**
     * Coordenador
     */
    public static final Role MANAGER = Role.valueOf("MANAGER");
    /**
     * Colaborador
     */
    public static final Role EMPLOYEE = Role.valueOf("EMPLOYEE");
    /**
     * Diretor
     */


    public static final Role DIRECTOR = Role.valueOf("DIRECTOR");


    /**
     * get available role types for adding new users
     *
     * @return
     */
    public static Role[] systemRoles() {
        return new Role[] {MANAGER, EMPLOYEE, DIRECTOR };
    }


}