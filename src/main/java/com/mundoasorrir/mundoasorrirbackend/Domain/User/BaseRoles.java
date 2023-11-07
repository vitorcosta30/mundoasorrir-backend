package com.mundoasorrir.mundoasorrirbackend.Domain.User;

public final class BaseRoles {
    /**
     * poweruser
     */
    public static final Role MANAGER = Role.valueOf(2,"MANAGER");
    /**
     * Utente
     */
    public static final Role EMPLOYEE = Role.valueOf(1,"EMPLOYEE");

    /**
     * get available role types for adding new users
     *
     * @return
     */
    public static Role[] systemRoles() {
        return new Role[] {MANAGER, EMPLOYEE };
    }


}