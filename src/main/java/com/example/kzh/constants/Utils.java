package com.example.kzh.constants;

import com.example.kzh.entities.User;
import com.example.kzh.security.Permission;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {
    public static User getCurrentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) return null;

        return (User) authentication.getPrincipal();
    }

    public static boolean hasPermission(User user, Permission permission) {
        if(user == null) return false;
        if(permission == null) return false;

        return user.getRole().getPermissions().contains(permission);
    }

}
