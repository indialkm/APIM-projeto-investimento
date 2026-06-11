package com.investimento.app.service;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.investimento.app.model.Usuario;
import com.investimento.app.repository.UsuarioRepository;


@Service
public class AuthenticationService {
    private final JwtService jwtService;
    private final UsuarioRepository userRepository;
    
    public AuthenticationService(JwtService jwtService, UsuarioRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }
    
    public String authenticate(Authentication authentication) {
        String username = authentication.getName();     
        Usuario user = userRepository.findByEmail(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return jwtService.generateToken(user);
    }
}
