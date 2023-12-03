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

    public Boolean permissionCheck(int permissionSet, HttpServletRequest request){
        return PermissionManager.isAllowed(getUserFromRequest(request),permissionSet);
    }

    public SystemUser getUserFromRequest(HttpServletRequest request){
        String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwtFromCookies(request));
        return this.userService.findUserByUsername(username);
    }
}
