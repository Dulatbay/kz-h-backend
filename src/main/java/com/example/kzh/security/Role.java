package com.example.kzh.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.kzh.security.Permission.*;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER(QUIZ_PERMISSIONS),
    ADMIN(mergePermissions(USER.permissions, Set.of(MODULE_CREATE, QUIZ_VERIFY)));


    private final Set<Permission> permissions;

    @SafeVarargs
    private static Set<Permission> mergePermissions(Set<Permission>... permissions) {
        return Stream.of(permissions)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.permission))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
