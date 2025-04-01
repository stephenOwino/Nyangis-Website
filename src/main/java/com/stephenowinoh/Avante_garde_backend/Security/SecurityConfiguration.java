package com.stephenowinoh.Avante_garde_backend.Security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration implements WebMvcConfigurer {

        @Autowired
        private UserDetailsService userDetailsService;

        @Autowired
        private JwtAuthenticationFilter jwtAuthenticationFilter;

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder(12);
        }

        @Bean
        public AuthenticationProvider authenticationProvider() {
                DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
                provider.setPasswordEncoder(passwordEncoder());
                provider.setUserDetailsService(userDetailsService);
                return provider;
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
                return config.getAuthenticationManager();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http
                        .csrf(AbstractHttpConfigurer::disable)
                        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/register", "/login").permitAll()
                                .requestMatchers("/chat/**").permitAll()
                                .requestMatchers("/api/products").permitAll()
                                .requestMatchers("/api/products/category/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll() // Allow GET requests to /api/products/{id}
                                .requestMatchers("/api/products/uploads/**").permitAll()
                                .requestMatchers("/uploads/**").permitAll() // Add this for static files
                                .requestMatchers(HttpMethod.POST, "/api/products/upload").authenticated()
                                .requestMatchers(HttpMethod.POST, "/api/products/**").authenticated()
                                .requestMatchers(HttpMethod.PUT, "/api/products/**").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/api/products/**").authenticated()
                                .anyRequest().authenticated()
                        )
                        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                        .exceptionHandling(ex -> ex
                                .authenticationEntryPoint((req, res, e) -> {
                                        System.out.println("Authentication failed for " + req.getRequestURI());
                                        res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Required");
                                })
                                .accessDeniedHandler((req, res, e) -> {
                                        System.out.println("Access denied for " + req.getRequestURI());
                                        res.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                                })
                        )
                        .build();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of("http://localhost:5173")); // Adjust if frontend port differs
                configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(List.of("*"));
                configuration.setAllowCredentials(true);
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }

        @Override
        public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173") // Adjust if frontend port differs
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
        }
}
