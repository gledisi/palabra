package com.palabra.user.security.model;

import lombok.Builder;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Builder
public class PalabraUserDetail implements UserDetails, CredentialsContainer {

    private static final long serialVersionUID = 1L;
    private final String mobile;
    private String activationCode;
    private final Collection<PalabraAuthority> authority;
    private final boolean valid;


    @Override
    public void eraseCredentials() {
        this.activationCode = null;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authority;
    }

    @Override
    public String getPassword() {
        return activationCode;
    }

    @Override
    public String getUsername() {
        return mobile;
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
        return valid;
    }

}
