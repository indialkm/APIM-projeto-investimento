package com.investimento.app.model;


import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

public class UserAuthenticated implements UserDetails {

	private final Usuario usuario;
	
	public UserAuthenticated(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUser() {
        return usuario;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return usuario.getRoles().stream()
                .map(role -> (GrantedAuthority) () -> role.name())
                .toList();
    }

    @Override
    public String getPassword() {
        return usuario.getSenha();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
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
        return true;
    }
	
}
