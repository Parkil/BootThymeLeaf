package com.example.bootthymeleaf.repo;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Mapper
public interface SpringSecurityRepo {
    List<UserDetails> findListByUserId(String userId);

    UserDetails findByUserId(String userId);
}
