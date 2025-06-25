package com.winnerezy.rae.config;

import com.winnerezy.rae.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    public JwtFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService, HandlerExceptionResolver handlerExceptionResolver) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("request {}", request.getRequestURI());

        String authHeader = request.getHeader("Authorization");
       if(authHeader != null && authHeader.startsWith("Bearer ")) {
           String token = authHeader.substring(7);
           log.debug("token {}", token);

           String username = jwtUtil.extractUsername(token);
           if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
               log.info("username {}", username);
               try {
                   UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                   if(jwtUtil.validateToken(token, userDetails.getUsername())){
                       log.debug("authorities {}", userDetails.getAuthorities());
                       UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                       auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                       SecurityContextHolder.getContext().setAuthentication(auth);
                   }
               } catch(Exception e){
                   log.atError().log(e.getMessage());
                   handlerExceptionResolver.resolveException(request, response, null, e);
                   SecurityContextHolder.clearContext();
               }
           }
       }
        filterChain.doFilter(request, response);
    }
}
