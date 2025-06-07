package com.winnerezy.rae.config;

import com.winnerezy.rae.enums.Role;
import com.winnerezy.rae.models.User;
import com.winnerezy.rae.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;

@Configuration
@Slf4j
public class OauthConfig {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public OauthConfig(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }


    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauthService() throws OAuth2AuthenticationException {
         final DefaultOAuth2UserService defaultOAuth2UserService = new DefaultOAuth2UserService();
        return (userRequest) -> {
            log.info("OAUTH RUNNING");
            OAuth2User oAuth2User = defaultOAuth2UserService.loadUser(userRequest);
            String email = oAuth2User.getAttribute("email");
            String name = oAuth2User.getAttribute("name");

            User user = userRepository.findByUsername(email).orElseGet(() -> {
                User newUser = new User();
                newUser.setUsername(email);
                newUser.setName(name);
                newUser.setRole(Role.USER);
                return userRepository.save(newUser);
            });
            log.info("email {}", user.getUsername());
            log.info("name {}", user.getName());

            return new DefaultOAuth2User(
                    List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())),
                    oAuth2User.getAttributes(),
                    "email"
            );
        };


    };

    @Bean
    public OAuth2UserService<OidcUserRequest, OidcUser> oidcAuthService() throws OAuth2AuthenticationException {
        final OidcUserService oidcUserService = new OidcUserService();
        return (userRequest) -> {
            log.info("OIDCAUTH RUNNING");
            OidcUser oidcUser = oidcUserService.loadUser(userRequest);
            String email = oidcUser.getAttribute("email");
            String name = oidcUser.getAttribute("name");
            String profile = oidcUser.getAttribute("picture");

            User user = userRepository.findByUsername(email).orElseGet(() -> {
                User newUser = new User();
                newUser.setUsername(email);
                newUser.setName(name);
                newUser.setRole(Role.USER);
                newUser.setProfilePicture(profile);
                return userRepository.save(newUser);
            });

            jwtUtil.generateToken(user.getUsername(), user.getRole());
            log.info("email {}", user.getUsername());
            log.info("name {}", user.getName());

            return new DefaultOidcUser(
                    List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())),
                    oidcUser.getIdToken(),
                    oidcUser.getUserInfo(),
                    "email"
            );
        };


    };
}
