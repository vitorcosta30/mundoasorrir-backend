package com.mundoasorrir.mundoasorrirbackend.Domain.User;

public final class BaseRoles {
    /**
     * poweruser
     */
    public static final Role MANAGER = Role.valueOf("MANAGER");
    /**
     * Utente
     */
    public static final Role EMPLOYEE = Role.valueOf("EMPLOYEE");

    /**
     * get available role types for adding new users
     *
     * @return
     */
    public static Role[] systemRoles() {
        return new Role[] {MANAGER, EMPLOYEE };
    }


}