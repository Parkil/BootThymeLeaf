package com.example.bootthymeleaf.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@EqualsAndHashCode
@Getter
public class UserInfo implements UserDetails {
    private final int idx;
    private final String userId;
    private final String pwd;
    private final LocalDateTime regDtm;

    @Setter
    private Collection<? extends GrantedAuthority> authorities;

    public UserInfo(int idx, String userId, String pwd, LocalDateTime regDtm, Collection<? extends GrantedAuthority> authorities) {
        this.idx = idx;
        this.userId = userId;
        this.pwd = pwd;
        this.regDtm = regDtm;
        this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.pwd;
    }

    @Override
    public String getUsername() {
        return this.userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
        assert authorities != null;

        SortedSet<GrantedAuthority> sortedAuth = new TreeSet<>(new AuthorityComparator());

        sortedAuth.addAll(authorities);

        return sortedAuth;
    }

    private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {

        private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

        @Override
        public int compare(GrantedAuthority o1, GrantedAuthority o2) {
            if(o2.getAuthority() ==  null) {
                return -1;
            }

            if(o1.getAuthority() == null) {
                return 1;
            }

            return o1.getAuthority().compareTo(o2.getAuthority());
        }
    }
}