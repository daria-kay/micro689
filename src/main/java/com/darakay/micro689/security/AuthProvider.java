package com.darakay.micro689.security;

import com.darakay.micro689.domain.User;
import com.darakay.micro689.repo.UserRepository;
import com.google.common.collect.ImmutableSet;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class AuthProvider implements AuthenticationProvider {

    private final UserRepository userRepository;

    public AuthProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String[] credentials = getLoginAndPassword(authentication.getName());
        String login = credentials[0];
        String password = credentials[1];
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Несуществующий логин"));
        if(user.getPassword().equals(password)){
            return new PreAuthenticatedAuthenticationToken(user, authentication.getCredentials(),
                    ImmutableSet.of());
        }
        throw new BadCredentialsException("Неверный пароль");
    }

    private String[] getLoginAndPassword(String headerValue){
        String decoded = new String(Base64.getDecoder().decode(headerValue));
        return decoded.split(":");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
