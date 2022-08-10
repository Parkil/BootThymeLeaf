package com.example.bootthymeleaf.spring_security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PlainPasswordEncoder implements PasswordEncoder {
    private static final Logger logger = LoggerFactory.getLogger(PlainPasswordEncoder.class);

    @Override
    public String encode(CharSequence rawPassword) {
        String tempStr = rawPassword.toString();
        logger.info("rawPassword : {}", tempStr);
        return tempStr;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String tempStr = rawPassword.toString();
        logger.info("rawPassword : {}, encodedPassword : {}", tempStr, encodedPassword);
        return encodedPassword.equals(tempStr);
    }
}