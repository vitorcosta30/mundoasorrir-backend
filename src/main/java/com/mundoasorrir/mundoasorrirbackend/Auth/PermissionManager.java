package com.mundoasorrir.mundoasorrirbackend.Auth;

import com.mundoasorrir.mundoasorrirbackend.Domain.User.BaseRoles;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.Role;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import org.springframework.stereotype.Component;
@Component
public class PermissionManager {

    private static final Role DIRECTOR = BaseRoles.DIRECTOR;

    private static final Role MANAGER = BaseRoles.MANAGER;

    private static final Role EMPLOYEE = BaseRoles.EMPLOYEE;

    private static final Role[] ROLESET1 = new Role[]{DIRECTOR,MANAGER,EMPLOYEE};

    private static final Role[] ROLESET2 = new Role[]{DIRECTOR,MANAGER};
    private static final Role[] ROLESET3 = new Role[]{DIRECTOR};

    private final int LOW_PERMISSIONS = 1;
    private final int MEDIUM_PERMISSIONS = 2;
    private final int HIGH_PERMISSIONS = 3;

    public boolean isAllowed(SystemUser user, int permissionSet){
        Role userRole = user.getRole();
        if(permissionSet == LOW_PERMISSIONS){
            return lowPermissions(userRole);

        }
        if(permissionSet == MEDIUM_PERMISSIONS){
            return mediumPermissions(userRole);

        }
        if(permissionSet == HIGH_PERMISSIONS){
            return highPermissions(userRole);
        }
        return false;
    }
    private boolean lowPermissions(Role role){
        return checkIfRoleIsIn(role,ROLESET1);

    }

    public int hasLowPermissions(){
        return this.LOW_PERMISSIONS;
    }
    public int hasMediumPermissions(){
        return this.LOW_PERMISSIONS;
    }
    public int hasHighPermissions(){
        return this.LOW_PERMISSIONS;
    }


    private boolean mediumPermissions(Role role){
        return checkIfRoleIsIn(role,ROLESET2);

    }
    private  boolean highPermissions(Role role){
        return checkIfRoleIsIn(role,ROLESET3);
    }

    private boolean checkIfRoleIsIn(Role role, Role[] rolesAccepted){
        for(int i = 0; i < rolesAccepted.length; i++){
            if(rolesAccepted[i].getName().equals(role.getName())){
                return true;

            }
        }
        return false;
    }

}
