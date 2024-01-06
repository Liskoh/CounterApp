package me.liskoh.counter.constants;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public enum RoleEnum {

    DEFAULT(null,
            Set.of(
                    PermissionEnum.ADD_SELF_COUNTER,
                    PermissionEnum.VIEW_SELF_COUNTER
            )),
    MODERATOR(DEFAULT, Set.of(
            PermissionEnum.ADD_SELF_COUNTER,
            PermissionEnum.VIEW_SELF_COUNTER,
            PermissionEnum.ADD_OTHER_COUNTER,
            PermissionEnum.VIEW_OTHER_COUNTER
    )),
    ADMINISTRATOR(MODERATOR, Set.of(
            PermissionEnum.ADD_PERMISSION,
            PermissionEnum.REMOVE_PERMISSION
    ));

    private Set<SimpleGrantedAuthority> authorities;

    RoleEnum(RoleEnum inherits, Set<PermissionEnum> permissions) {
        this.authorities = new HashSet<>();
        if (inherits != null) {
            this.authorities = inherits.getAuthorities()
                    .stream()
                    .filter(authority -> !authority.getAuthority().startsWith(Constants.ROLE_PREFIX))
                    .collect(Collectors.toSet());
        }

        this.authorities.addAll(permissions
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet()));

        /* ROLE_ prefix is required by Spring Security */
        this.authorities.add(new SimpleGrantedAuthority(Constants.ROLE_PREFIX + this.name()));
    }
}
