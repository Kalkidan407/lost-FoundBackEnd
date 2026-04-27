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
        FilterChain filterChain)
        throws ServletException, IOException {

 String path = request.getServletPath();
     logger.info("PATH: {}", request.getServletPath());

if (path.startsWith("/api/auth")) {
    filterChain.doFilter(request, response);
    return;
}

    logger.info("JWT FILTER EXECUTED");

    final String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        logger.warn("NO AUTH HEADER");
        filterChain.doFilter(request, response);
        return;
    }

    try{

    String jwt = authHeader.substring(7).trim();

    String email = jwtService.extractUsername(jwt);

    logger.debug("Extracted Email: {}", email);

    if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        if (jwtService.isTokenValid(jwt, userDetails)) {

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }
} catch (Exception e) {
    logger.error("JWT ERROR: {}", e.getMessage());
    // ❗ Do NOT block request, just continue

 }     


    filterChain.doFilter(request, response);
}

}
