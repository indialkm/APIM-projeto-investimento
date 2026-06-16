package com.investimento.app.service;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import com.investimento.app.model.UserAuthenticated;
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
    
    public Long getUsuarioIdLogado() {
        // 1. Pega a autenticação completa do contexto
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("Nenhum usuário autenticado no contexto.");
        }

        // 2. Se a autenticação for do tipo JWT nativo do Spring OAuth2 (o mais provável)
        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            org.springframework.security.oauth2.jwt.Jwt jwt = jwtAuth.getToken();
            Number userId = jwt.getClaim("userId");
            if (userId != null) {
                return userId.longValue();
            }
        }

        // 3. Plano de fuga definitivo: Se o Spring escondeu o ID, pegamos o e-mail/username
        // que SEMPRE vem no .getName() de qualquer autenticação do Spring, e buscamos o ID no banco.
        String email = authentication.getName(); 
        if (email != null && !email.isBlank() && !email.equals("anonymousUser")) {
            return userRepository.findByEmail(email)
                    .map(Usuario::getId)
                    .orElseThrow(() -> new IllegalStateException("Usuário não encontrado para o login: " + email));
        }
        throw new IllegalStateException("Não foi possível identificar o usuário logado através da autenticação atual.");
    }
    
}
