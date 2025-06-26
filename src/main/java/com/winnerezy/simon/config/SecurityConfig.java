package com.winnerezy.simon.config;

import com.winnerezy.simon.security.OAuthSuccessHandler;
import com.winnerezy.simon.security.RestAccessDenied;
import com.winnerezy.simon.security.RestAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final RestAccessDenied restAccessDenied;
    private final OauthConfig oauthConfig;
    private final OAuthSuccessHandler authSuccessHandler;

    public SecurityConfig(JwtFilter jwtFilter,
                          RestAuthenticationEntryPoint restAuthenticationEntryPoint,
                          RestAccessDenied restAccessDenied,
                          OauthConfig oauthConfig,
                          OAuthSuccessHandler authSuccessHandler){
        this.jwtFilter = jwtFilter;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
        this.restAccessDenied = restAccessDenied;
        this.oauthConfig = oauthConfig;
        this.authSuccessHandler = authSuccessHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/v1/auth/**", "/login/**", "/oauth2/**", "/auth/**", "/swagger-ui/**", "/v3/**").permitAll()
                                .requestMatchers("/api/v1/feedback/**", "/api/v1/ai/**", "/api/v1/session/**", "/api/v1/user/**")
                                .authenticated()
                                .anyRequest().denyAll()
                ).oauth2Login(oauth -> oauth
                        .successHandler(authSuccessHandler)
                        .userInfoEndpoint(userInfo -> {
                            userInfo.userService(oauthConfig.oauthService());
                            userInfo.oidcUserService(oauthConfig.oidcAuthService());
                        }))
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(restAuthenticationEntryPoint)
                        .accessDeniedHandler(restAccessDenied))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000", "https://simon-therapist.vercel.app"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
