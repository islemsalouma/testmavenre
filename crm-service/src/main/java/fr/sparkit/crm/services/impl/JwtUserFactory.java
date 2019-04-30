package fr.sparkit.crm.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import fr.sparkit.crm.entities.Authority;
import fr.sparkit.crm.entities.Permission;
import fr.sparkit.crm.entities.User;

public final class JwtUserFactory {

    private JwtUserFactory() {

    }

    public static JwtUser create(User user) {
        return new JwtUser(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail(),
                "", getAuthorities(user.getAuthorities()), user.isEnabled(), user.isFirstLogin(), null,
                user.getLanguage());
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(final Collection<Authority> roles) {
        return getGrantedAuthorities(getPermissions(roles));
    }

    private static List<String> getPermissions(final Collection<Authority> roles) {
        final List<String> permissions = new ArrayList<>();
        final List<Permission> collection = new ArrayList<>();
        for (final Authority role : roles) {
            collection.addAll(role.getPermissions());
        }
        for (final Permission item : collection) {
            permissions.add(item.getPermissionName());
        }

        return permissions;
    }

    private static List<GrantedAuthority> getGrantedAuthorities(final List<String> privileges) {
        final List<GrantedAuthority> authorities = new ArrayList<>();
        for (final String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}
