package com.example.bootthymeleaf.boot_config;

import com.example.bootthymeleaf.spring_security.CustomJdbcDaoImpl;
import com.example.bootthymeleaf.spring_security.CustomOAuth2UserService;
import com.example.bootthymeleaf.spring_security.PlainPasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain setHttpSecurity(final HttpSecurity http) throws Exception {
        /*
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
                .defaultSuccessUrl("/dialect/insert", true)
                .failureUrl("/login?error=true")
                //.failureHandler(authenticationFailureHandler())
                .and()
                .logout()
                .logoutUrl("/perform_logout")
                .permitAll()
                .deleteCookies("JSESSIONID");
                //.logoutSuccessHandler(logoutSuccessHandler());


        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(2)
                .expiredUrl("/login?error=session_expired");

        return http.build();
        <a href="/oauth2/authorization/naver">Naver</a>
         */

        //loginProcessingUrl 을 [/] 로 지정 할 경우 이상한 url이 호출되는 경우가 있다
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

//    @Bean
//    public CustomOAuth2UserService customOAuth2UserService() {
//        return new CustomOAuth2UserService();
//    }

    //bean 을 선언하는 순간 password encoder 가 지정된다
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PlainPasswordEncoder();
//        return new BCryptPasswordEncoder();
    }

    //todo 로그인 세션 유효기간 설정
    //todo spring security 에서 세션을 사용하는지 확인 필요


}