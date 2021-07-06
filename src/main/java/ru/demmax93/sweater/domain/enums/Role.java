package ru.demmax93.sweater.domain.enums;

import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum Role implements GrantedAuthority {
    USER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }

    public static Set<String> getNames() {
        return Arrays.stream(values()).map(Role::name).collect(Collectors.toSet());
    }
}
