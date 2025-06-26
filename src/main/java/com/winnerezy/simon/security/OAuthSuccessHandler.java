package com.winnerezy.simon.security;

import com.winnerezy.simon.enums.Role;
import com.winnerezy.simon.utils.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    public OAuthSuccessHandler(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    ) throws IOException, ServletException {
        OidcUser user = (OidcUser) authentication.getPrincipal();

        String token = jwtUtil.generateToken(user.getEmail(), Role.USER);

        // response.sendRedirect("http://localhost:3000/success?token=" + token);
        response.sendRedirect(
            "https://simon-therapist.vercel.app/success?token=" + token
        );
    }
}
