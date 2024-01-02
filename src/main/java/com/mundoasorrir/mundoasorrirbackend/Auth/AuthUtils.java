package com.mundoasorrir.mundoasorrirbackend.Auth;

import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import com.mundoasorrir.mundoasorrirbackend.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthUtils {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionManager permissionManager;

    public Boolean lowPermissions(HttpServletRequest request){
        return permissionManager.isAllowed(getUserFromRequest(request),this.permissionManager.hasLowPermissions());
    }
    public Boolean mediumPermissions(HttpServletRequest request){
        return permissionManager.isAllowed(getUserFromRequest(request),this.permissionManager.hasMediumPermissions());
    }
    public Boolean highPermissions(HttpServletRequest request){
        return permissionManager.isAllowed(getUserFromRequest(request),this.permissionManager.hasHighPermissions());
    }

    public Boolean isLoggedIn(HttpServletRequest request){
        SystemUser user = getUserFromRequest(request);
        return (!jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwtFromCookies(request)).isEmpty()) && user != null && user.isActive();
    }
    public SystemUser getUserFromRequest(HttpServletRequest request){
        String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwtFromCookies(request));
        return this.userService.findUserByUsername(username);
    }
}
