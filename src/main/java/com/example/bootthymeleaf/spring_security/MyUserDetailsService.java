package com.example.bootthymeleaf.spring_security;

import com.example.bootthymeleaf.repo.SpringSecurityRepo;
import com.example.bootthymeleaf.vo.UserInfo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MyUserDetailsService implements UserDetailsService {

    private final HttpServletRequest request;

    private final SpringSecurityRepo springSecurityRepo;

    private final Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);

    private static final String USER_ROLE = "USER";

    @Override
    public UserDetails loadUserByUsername(final String userName) throws UsernameNotFoundException {
        final String ip = getClientIP();
        logger.info("current ip : {}", ip);
        logger.info("userName : {}",userName);

        UserDetails userDetails = springSecurityRepo.findByUserId(userName);
        logger.info("userDetails : {}", userDetails);

        if(userDetails == null) {
            throw new UsernameNotFoundException("No user found with username: " + userName);
        }

        UserInfo userInfo = (UserInfo) userDetails;
        userInfo.setAuthorities(loadUserAuthorities());

        return userDetails;
    }

    private List<GrantedAuthority> loadUserAuthorities() {
        List<GrantedAuthority> retList = new ArrayList<>();
        retList.add(new SimpleGrantedAuthority(USER_ROLE));
        return retList;
    }

    private String getClientIP() {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader != null) {
            return xfHeader.split(",")[0];
        }
        return request.getRemoteAddr();
    }
}