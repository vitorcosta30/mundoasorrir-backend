package com.mundoasorrir.mundoasorrirbackend.Auth;

import com.mundoasorrir.mundoasorrirbackend.Domain.User.BaseRoles;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.Role;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import org.springframework.data.util.Pair;

import java.util.List;

public class PermissionManager {

    private static final Role DIRECTOR = BaseRoles.DIRECTOR;

    private static final Role MANAGER = BaseRoles.MANAGER;

    private static final Role EMPLOYEE = BaseRoles.EMPLOYEE;

    private static final Role[] ROLESET1 = new Role[]{DIRECTOR,MANAGER,EMPLOYEE};

    private static final Role[] ROLESET2 = new Role[]{DIRECTOR,MANAGER};
    private static final Role[] ROLESET3 = new Role[]{DIRECTOR};

    public static boolean isAllowed(SystemUser user, int permissionSet){
        Role userRole = user.getRole();
        if(permissionSet == 1){
            return lowPermissions(userRole);

        }
        if(permissionSet == 2){
            return mediumPermissions(userRole);

        }
        if(permissionSet == 3){
            return highPermissions(userRole);
        }
        return false;
    }
    private static boolean lowPermissions(Role role){
        return checkIfRoleIsIn(role,ROLESET1);

    }

    private static boolean mediumPermissions(Role role){
        return checkIfRoleIsIn(role,ROLESET2);

    }
    private static boolean highPermissions(Role role){
        return checkIfRoleIsIn(role,ROLESET3);
    }

    private static boolean checkIfRoleIsIn(Role role, Role[] rolesAccepted){
        for(int i = 0; i < rolesAccepted.length; i++){
            if(rolesAccepted[i].getName().equals(role.getName())){
                return true;

            }
        }
        return false;
    }

}
