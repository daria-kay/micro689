package com.darakay.micro689.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${frontend.host}")
    private String frontendHost;

    @Value("${authentication.header-name}")
    private String authHeaderName;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin().loginPage("/api/v1/login")
                .and()
                .authorizeRequests().antMatchers("/api/v1/logup", "/api/v1/black-list/find-matches-task")
                .permitAll()
                .and()
                .authorizeRequests().antMatchers("/api/v1/black-list", "/api/v1/black-list/*","/api/v1/black-list/*/*" ).authenticated()
                .and()
                .addFilterAfter(filter(), RequestHeaderAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors().configurationSource(corsConfigurationSource())
                .and().httpBasic();
    }

    private CorsConfigurationSource corsConfigurationSource(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsForCreateRecord = new CorsConfiguration();
        corsForCreateRecord.addAllowedMethod(HttpMethod.POST);
        corsForCreateRecord.addAllowedOrigin(frontendHost);
        corsForCreateRecord.addAllowedHeader(authHeaderName);
        source.registerCorsConfiguration("/api/v1/black-list/add-entry-task", corsForCreateRecord);
        source.registerCorsConfiguration("/api/v1/black-list/upload-task", corsForCreateRecord);

        CorsConfiguration corsUpdateRecord = new CorsConfiguration();
        corsUpdateRecord.addAllowedMethod(HttpMethod.PUT);
        corsUpdateRecord.addAllowedOrigin(frontendHost);
        corsUpdateRecord.addAllowedHeader(authHeaderName);
        source.registerCorsConfiguration("/api/v1/black-list/*/*", corsUpdateRecord);

        CorsConfiguration corsDeleteRecord = new CorsConfiguration();
        corsDeleteRecord.addAllowedMethod(HttpMethod.DELETE);
        corsDeleteRecord.addAllowedOrigin(frontendHost);
        corsDeleteRecord.addAllowedHeader(authHeaderName);
        source.registerCorsConfiguration("/api/v1/black-list/*", corsDeleteRecord);

        CorsConfiguration corsGetRecords = new CorsConfiguration();
        corsGetRecords.addAllowedMethod(HttpMethod.GET);
        corsGetRecords.addAllowedOrigin(frontendHost);
        corsGetRecords.addAllowedHeader(authHeaderName);
        source.registerCorsConfiguration("/api/v1/black-list", corsGetRecords);

        CorsConfiguration corsFindMatches = new CorsConfiguration();
        corsFindMatches.addAllowedMethod(HttpMethod.POST);
        source.registerCorsConfiguration("/api/v1/black-list/find-matches-task", corsFindMatches);

        CorsConfiguration corsForLoginLogup = new CorsConfiguration();
        corsForLoginLogup.addAllowedMethod(HttpMethod.POST);
        source.registerCorsConfiguration("/api/v1/logup", corsForLoginLogup);

        return source;
    }

    private RequestHeaderAuthenticationFilter filter() throws Exception {
        final RequestHeaderAuthenticationFilter filter = new RequestHeaderAuthenticationFilter();
        filter.setExceptionIfHeaderMissing(false);
        filter.setPrincipalRequestHeader(authHeaderName);
        filter.setAuthenticationManager(this.authenticationManager());
        return filter;
    }
}
