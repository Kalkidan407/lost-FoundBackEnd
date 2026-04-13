package com.lostfound.lostfound.security;


import java.io.IOException;
import java.util.List;


import jakarta.servlet.*;
import jakarta.servlet.http.*;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private static final  Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

 @SuppressWarnings("null")
@Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain  )
            throws ServletException, IOException {

                logger.info("JWT FILTER EXECUTED");

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

                 logger.warn("NO AUTH HEADER");

            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader;

        String email = jwtService.extractUsername(jwt);

        Claims claims = jwtService.extractAllClaims(jwt);
       String role = claims.get("role", String.class); 


           logger.debug("Extracted Email: {email}");

        if (email != null
                && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(email);

            if (jwtService.isTokenValid( jwt, userDetails )) {
                  
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                List.of(authority)
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                SecurityContextHolder.getContext()
                        .setAuthentication(authToken);
            }

        }

        filterChain.doFilter(request, response);
    }
}
