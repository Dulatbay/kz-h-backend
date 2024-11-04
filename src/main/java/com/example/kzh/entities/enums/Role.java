package com.example.kzh.entities.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.kzh.entities.enums.Permission.MODULE_CREATE;
import static com.example.kzh.entities.enums.Permission.QUIZ_PERMISSIONS;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER(QUIZ_PERMISSIONS),
    ADMIN(mergePermissions(USER.permissions, Set.of(MODULE_CREATE)));


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
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
