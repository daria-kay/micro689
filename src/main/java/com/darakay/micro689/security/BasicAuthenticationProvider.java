package com.darakay.micro689.security;

import com.darakay.micro689.domain.User;
import com.darakay.micro689.repo.UserRepository;
import com.google.common.collect.ImmutableSet;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BasicAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;

    public BasicAuthenticationProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Несуществующий логин"));
        if(user.getPassword().equals(password) && !user.isBlock()){
            return new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(),
                    ImmutableSet.of(new SimpleGrantedAuthority("USER")));
        }
        if(user.isBlock())
            throw new BadCredentialsException("Пользователь заблокирован");
        throw new BadCredentialsException("Неверный пароль");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
