package com.investimento.app.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.investimento.app.model.UserAuthenticated;
import com.investimento.app.model.Usuario;
import com.investimento.app.repository.UsuarioRepository;

@Service
public class UserDetailService implements UserDetailsService {
    private final UsuarioRepository userRepository;

    private UserDetailService(UsuarioRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new UserAuthenticated(user);
    }
}
