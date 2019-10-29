package com.darakay.micro689.config;

import com.darakay.micro689.security.AuthProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final AuthProvider authProvider;

    public WebSecurityConfiguration(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin().loginPage("/api/v1/login")
                .and()
                .authorizeRequests().antMatchers("/api/v1/logup", "/api/v1/black-list/find-matches-task")
                .permitAll()
                .and()
                .authorizeRequests().antMatchers("/api/v1/black-list/*").authenticated()
                .and()
                .addFilterAfter(filter(), RequestHeaderAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().httpBasic();
    }

    private RequestHeaderAuthenticationFilter filter() throws Exception {
        final RequestHeaderAuthenticationFilter filter = new RequestHeaderAuthenticationFilter();
        filter.setExceptionIfHeaderMissing(false);
        filter.setPrincipalRequestHeader("XXX-Authentication");
        filter.setAuthenticationManager(this.authenticationManager());
        return filter;
    }
}
