package com.investimento.app.config;

import com.investimento.app.security.CustomJwtAuthenticationConverter;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.source.ImmutableJWKSet;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Value("${jwt.public.key}")
	private RSAPublicKey key;
	
	@Value("${jwt.private.key}")
	private RSAPrivateKey priv;
	
	@Bean
    public CustomJwtAuthenticationConverter customJwtAuthenticationConverter() {
        return new CustomJwtAuthenticationConverter();
    }
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
            CustomJwtAuthenticationConverter customJwtAuthenticationConverter) throws Exception {
        http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
              
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/auth/authenticate").permitAll()
                .requestMatchers("/api/users/register").permitAll()
                
                
                /*.anyRequest().authenticated())*/
                .anyRequest().permitAll()) //Por enquanto deixando todas as rotas abertas porque estamos criando as funcionalidades ainda, terminar o proejto não podemos esquecer de fechar
                
                .oauth2ResourceServer(
                        conf -> conf.jwt(jwt -> jwt.jwtAuthenticationConverter(customJwtAuthenticationConverter)))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
	
	 @Bean
	    PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	    
	    @Bean
	    JwtDecoder jwtDecoder() {
	        return NimbusJwtDecoder.withPublicKey(this.key).build();
	    }
	    
	    @Bean
	    JwtEncoder jwtEncoder() {
	        var jwk = new RSAKey.Builder(this.key).privateKey(this.priv).build();
	        var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
	        return new NimbusJwtEncoder(jwks);
	    }

}
