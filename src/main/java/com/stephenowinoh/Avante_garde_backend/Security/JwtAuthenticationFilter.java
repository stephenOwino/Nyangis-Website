package com.stephenowinoh.Avante_garde_backend.Security;

import com.stephenowinoh.Avante_garde_backend.MyUserDetailService;
import jakarta.servlet.ServletException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

        @Autowired
        private JwtService jwtService;

        @Autowired
        private MyUserDetailService myUserDetailService;

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                String authHeader = request.getHeader("Authorization");
                String path = request.getRequestURI();
                System.out.println("Processing request: " + path + " | Auth Header: " + authHeader);

                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                        System.out.println("No valid token, proceeding: " + path);
                        filterChain.doFilter(request, response);
                        return;
                }

                String jwt = authHeader.substring(7);
                String username = jwtService.extractUsername(jwt);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UserDetails userDetails = myUserDetailService.loadUserByUsername(username);
                        if (userDetails != null && jwtService.isTokenValid(jwt)) {
                                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                        username, null, userDetails.getAuthorities()
                                );
                                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                                System.out.println("Authentication set for: " + username + " on " + path);
                        } else {
                                System.out.println("Token invalid or user not found: " + username + " on " + path);
                        }
                }
                filterChain.doFilter(request, response);
        }

        @Override
        protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
                String path = request.getRequestURI();
                boolean skip = path.equals("/login") ||
                        path.equals("/register") ||
                        path.startsWith("/chat/") ||
                        path.equals("/api/products") ||
                        path.startsWith("/api/products/category/") ||
                        path.startsWith("/api/products/uploads/");
                System.out.println("Filter check for " + path + " | Skip: " + skip);
                return skip;
        }

}