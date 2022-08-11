package com.example.bootthymeleaf.spring_security;

import com.example.bootthymeleaf.repo.SpringSecurityRepo;
import com.example.bootthymeleaf.vo.UserInfo;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

import javax.sql.DataSource;
import java.util.*;

/*
    UserDetailsManager 와 JdbcDaoImpl 의 차이
    JdbcDaoImpl : 사용자 정보 조회 + 권한/그룹 부여가 주역할
    UserDetailsManager : 사용자 정보 관리(사용자 등록/수정/삭제/패스워드 변경) 가 주역할
 */
public class CustomJdbcDaoImpl extends JdbcDaoImpl {

    private static final String USER_ROLE = "USER";

    /*
        원래 JdbcDaoImpl 에서는 JdbcTemplate 을 이용하도록 되어 있으나 mybatis 를 이용하려면 다음과 같이 setter를 설정한다
     */
    @Setter(onMethod_=@Autowired)
    private SpringSecurityRepo springSecurityRepo;

    public CustomJdbcDaoImpl(DataSource dataSource) {
        setDataSource(dataSource);
        setEnableGroups(false);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetails> userList = loadUsersByUsername(username);
        if (userList.isEmpty()) {
            this.logger.debug("Query returned no results for user '" + username + "'");
            throw new UsernameNotFoundException(this.messages.getMessage("JdbcDaoImpl.notFound",
                    new Object[] { username }, "Username {0} not found"));
        }

        UserInfo userInfo = (UserInfo)userList.get(0); // contains no GrantedAuthority[]
        Set<GrantedAuthority> dbAuthsSet = new HashSet<>();

        if(super.getEnableAuthorities()) {
            dbAuthsSet.addAll(loadUserAuthorities(userInfo.getUsername()));
        }

        if(super.getEnableGroups()) {
            dbAuthsSet.addAll(loadGroupAuthorities(userInfo.getUsername()));
        }

        List<GrantedAuthority> dbAuths = new ArrayList<>(dbAuthsSet);
        userInfo.setAuthorities(dbAuths);

        if (dbAuths.isEmpty()) {
            this.logger.debug("User '" + username + "' has no authorities and will be treated as 'not found'");
            throw new UsernameNotFoundException(this.messages.getMessage("JdbcDaoImpl.noAuthority",
                    new Object[] { username }, "User {0} has no GrantedAuthority"));
        }

        return userInfo;
    }

    @Override
    protected List<GrantedAuthority> loadUserAuthorities(String username) {
        List<GrantedAuthority> retList = new ArrayList<>();
        retList.add(new SimpleGrantedAuthority(USER_ROLE));
        return retList;
    }

    @Override
    protected List<UserDetails> loadUsersByUsername(String username) {
        List<UserDetails> userInfoList = springSecurityRepo.findListByUserId(username);
        userInfoList.forEach(row -> ((UserInfo)row).setAuthorities(AuthorityUtils.NO_AUTHORITIES));

        return userInfoList;
    }
}