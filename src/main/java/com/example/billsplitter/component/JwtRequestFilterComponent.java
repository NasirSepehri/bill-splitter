package com.example.billsplitter.component;

import com.example.billsplitter.dto.client.JwtUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilterComponent extends OncePerRequestFilter {


    private final JwtTokenComponent jwtTokenComponent;
    private final JwtUserDetailsComponent jwtUserDetailsComponent;


    public JwtRequestFilterComponent(JwtTokenComponent jwtTokenComponent, JwtUserDetailsComponent jwtUserDetailsComponent) {
        this.jwtTokenComponent = jwtTokenComponent;
        this.jwtUserDetailsComponent = jwtUserDetailsComponent;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final @NotNull HttpServletResponse response,
                                    final @NotNull FilterChain chain) throws ServletException, IOException {
        // look for Bearer auth header
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        final String token = header.substring(7);
        final String username = jwtTokenComponent.validateTokenAndGetUsername(token);
        if (username == null) {
            // validation failed or token expired
            chain.doFilter(request, response);
            return;
        }

        // set user details on spring security context
        final JwtUserDetails userDetails = jwtUserDetailsComponent.loadUserByUsername(username);
        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // continue with authenticated user
        chain.doFilter(request, response);
    }

}