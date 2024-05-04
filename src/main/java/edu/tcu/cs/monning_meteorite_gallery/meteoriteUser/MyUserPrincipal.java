package edu.tcu.cs.monning_meteorite_gallery.meteoriteUser;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;

public class MyUserPrincipal implements UserDetails {

    private MeteoriteUser meteoriteUser;

    public MyUserPrincipal(MeteoriteUser meteoriteUser) {
        this.meteoriteUser = meteoriteUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convert a user's roles from space-delimited string to a list of SimpleGrantedAuthority objects.
        // E.g., john's roles are stored in a string like "admin user moderator", we need to convert it to a list of GrantedAuthority.
        // Before conversion, we need to add this "ROLE_" prefix to each role name.
        return Arrays.stream(StringUtils.tokenizeToStringArray(this.meteoriteUser.getRoles(), " "))
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .toList();

    }

    @Override
    public String getPassword() {
        return this.meteoriteUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.meteoriteUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.meteoriteUser.isEnabled();
    }

    public MeteoriteUser getMeteoriteUser() {return meteoriteUser;}
}

