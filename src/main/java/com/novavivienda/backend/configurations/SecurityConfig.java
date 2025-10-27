package com.novavivienda.backend.configurations;

import com.novavivienda.backend.filters.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        return http
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers(
                            "/swagger-ui/**",
                            "/v3/api-docs/**",
                            "/api/v1/authentication",
                            "/api/v1/authentication/**",
                            "/api/v1/payments/**"
                    ).permitAll();
//                    authorize.requestMatchers(HttpMethod.GET, "/api/v1/categories").permitAll();
//                    authorize.requestMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll();
//                    authorize.requestMatchers(HttpMethod.GET, "/api/v1/categories/{categoryId:\\d+}").permitAll();
//                    authorize.requestMatchers(HttpMethod.GET, "/api/v1/products").permitAll();
//                    authorize.requestMatchers(HttpMethod.GET, "/api/v1/products/**").permitAll();
//                    authorize.requestMatchers(HttpMethod.GET, "/api/v1/products/{productId:\\d+}").permitAll();
//                    authorize.requestMatchers(HttpMethod.GET, "/api/v1/products/{productId:\\d+}/likes").permitAll();
//                    authorize.requestMatchers(HttpMethod.GET, "/api/v1/products/{productId:\\d+}/images").permitAll();
//                    authorize.requestMatchers(HttpMethod.GET, "/api/v1/products/images/**").permitAll();
//                    authorize.requestMatchers(HttpMethod.GET, "/api/v1/products/images/{imageId:\\d+}").permitAll();
                    authorize.anyRequest().authenticated();
                })
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOriginPatterns(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("*"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.withDefaultRolePrefix()
                .role("SYSTEM_ADMIN").implies("AGENCY_ADMIN")
                .role("AGENCY_ADMIN").implies("CLIENT")
                .build();
    }

    @Bean
    public DefaultMethodSecurityExpressionHandler expressionHandler() {
        DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy());
        return handler;
    }
}
