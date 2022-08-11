package com.example.bootthymeleaf.boot_config;

import com.example.bootthymeleaf.spring_security.CustomJdbcDaoImpl;
import com.example.bootthymeleaf.spring_security.PlainPasswordEncoder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain setHttpSecurity(final HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/anonymous*").anonymous()
                .antMatchers("/login*").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/dialect/list", true)
                .failureUrl("/login?error=true")
                //.failureHandler(authenticationFailureHandler())
                .and()
                .logout()
                .logoutUrl("/perform_logout")
                .deleteCookies("JSESSIONID");
                //.logoutSuccessHandler(logoutSuccessHandler());

        return http.build();
    }


    /*@Bean
    public InMemoryUserDetailsManager setInMemoryUser() {
//        PasswordEncoder encoder = bCryptEncoder();

//        UserDetails user1 = User.builder().passwordEncoder(encoder::encode).username("user1").password(encoder.encode("user1Pass")).roles("USER").build();
//        UserDetails user2 = User.builder().passwordEncoder(encoder::encode).username("user2").password(encoder.encode("user2Pass")).roles("USER").build();
//        UserDetails user3 = User.builder().passwordEncoder(encoder::encode).username("user3").password(encoder.encode("user3Pass")).roles("ADMIN").build();

        UserDetails user1 = User.builder().username("user1").password("user1Pass").roles("USER").build();
        UserDetails user2 = User.builder().username("user2").password("user2Pass").roles("USER").build();
        UserDetails user3 = User.builder().username("user3").password("user3Pass").roles("ADMIN").build();

        return new InMemoryUserDetailsManager(user1, user2, user3);
    }*/

    /*
    @Bean
    public UserDetailsManager jdbcUserDetailManager(@Qualifier("getMySQLDataSource") DataSource mysqlDataSource) {
        return new CustomJdbcUserDetailsManager(mysqlDataSource);
    }*/

    @Bean
    public JdbcDaoImpl jdbcDaoImpl(@Qualifier("getMySQLDataSource") DataSource mysqlDataSource) {
        return new CustomJdbcDaoImpl(mysqlDataSource);
    }

    //bean 을 선언하는 순간 password encoder 가 지정된다
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PlainPasswordEncoder();
//        return new BCryptPasswordEncoder();
    }
}