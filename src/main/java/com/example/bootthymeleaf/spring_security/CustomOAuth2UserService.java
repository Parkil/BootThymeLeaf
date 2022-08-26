package com.example.bootthymeleaf.spring_security;

import com.example.bootthymeleaf.vo.TestUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("oAuth2User : "+oAuth2User);

        List<GrantedAuthority> retList = new ArrayList<>();
        retList.add(new SimpleGrantedAuthority("USER"));

        return new TestUser(oAuth2User.getAttributes(), retList, "response");
//        return new DefaultOAuth2User(retList, oAuth2User.getAttributes(), "response");
    }
}
