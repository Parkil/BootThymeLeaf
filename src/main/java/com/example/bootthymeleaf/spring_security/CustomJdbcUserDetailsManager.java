package com.example.bootthymeleaf.spring_security;

import com.example.bootthymeleaf.vo.UserInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.*;

public class CustomJdbcUserDetailsManager extends JdbcDaoImpl implements UserDetailsManager {

    private static final String DEF_USERS_BY_USERNAME_SQL = "select idx, user_id, pwd, reg_dtm from user_info where user_id = ?";

    private static final String USER_ROLE = "USER";

    public CustomJdbcUserDetailsManager(DataSource dataSource) {
        setDataSource(dataSource);
        setEnableGroups(false);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetails> users = loadUsersByUsername(username);
        if (users.isEmpty()) {
            this.logger.debug("Query returned no results for user '" + username + "'");
            throw new UsernameNotFoundException(this.messages.getMessage("JdbcDaoImpl.notFound",
                    new Object[] { username }, "Username {0} not found"));
        }

        UserInfo user = (UserInfo)users.get(0); // contains no GrantedAuthority[]
        Set<GrantedAuthority> dbAuthsSet = new HashSet<>();

        if(super.getEnableAuthorities()) {
            dbAuthsSet.addAll(loadUserAuthorities(user.getUsername()));
        }

        if(super.getEnableGroups()) {
            dbAuthsSet.addAll(loadGroupAuthorities(user.getUsername()));
        }

        List<GrantedAuthority> dbAuths = new ArrayList<>(dbAuthsSet);
        user.setAuthorities(dbAuths);

        if (dbAuths.isEmpty()) {
            this.logger.debug("User '" + username + "' has no authorities and will be treated as 'not found'");
            throw new UsernameNotFoundException(this.messages.getMessage("JdbcDaoImpl.noAuthority",
                    new Object[] { username }, "User {0} has no GrantedAuthority"));
        }

        return user;
    }

    @Override
    protected List<GrantedAuthority> loadUserAuthorities(String username) {
        List<GrantedAuthority> retList = new ArrayList<>();
        retList.add(new SimpleGrantedAuthority(USER_ROLE));
        return retList;
    }

    @Override
    protected List<UserDetails> loadUsersByUsername(String username) {
        RowMapper<UserDetails> mapper = (rs, rowNum) -> {
            int idx = rs.getInt(1);
            String userId = rs.getString(2);
            String pwd = rs.getString(3);
            Timestamp regDtm = rs.getTimestamp(4);
            return new UserInfo(idx, userId, pwd, regDtm.toLocalDateTime(), AuthorityUtils.NO_AUTHORITIES);
        };

        return getJdbc().query(DEF_USERS_BY_USERNAME_SQL, mapper, username);
    }

    private JdbcTemplate getJdbc() {
        return Optional.ofNullable(getJdbcTemplate()).orElseThrow(NullPointerException::new);
    }

    @Override
    public void createUser(UserDetails user) {

    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return true;
    }
}
