package com.example.bootthymeleaf.boot_config;

import com.example.bootthymeleaf.spring_security.CustomOAuth2UserService;
import com.example.bootthymeleaf.spring_security.MyUserDetailsService;
import com.example.bootthymeleaf.spring_security.PlainPasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final MyUserDetailsService myUserDetailsService;

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain setHttpSecurity(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login*").permitAll()
                .antMatchers("/login/oauth2/code/*").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/dialect/list").permitAll()
                .antMatchers("/fragment/list").permitAll()
                .antMatchers("/js/*").permitAll()
                .antMatchers("/css/*").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").loginProcessingUrl("/perform_login").defaultSuccessUrl("/dialect/banner")
                .and()
                .oauth2Login().loginPage("/login").defaultSuccessUrl("/dialect/banner").userInfoEndpoint().userService(customOAuth2UserService);

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(2)
                .expiredUrl("/login?error=session_expired");

        return http.build(); // generate spring security filters
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(myUserDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new PlainPasswordEncoder();
    }
}

