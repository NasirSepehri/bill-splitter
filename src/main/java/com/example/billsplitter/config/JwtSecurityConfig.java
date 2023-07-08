package com.example.billsplitter.config;

import com.example.billsplitter.component.JwtRequestFilterComponent;
import com.example.billsplitter.enums.ClientRolesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class JwtSecurityConfig {


    private static final String[] SWAGGER_PATHS = {"/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**", "/webjars/swagger-ui/**"};
    private final JwtRequestFilterComponent jwtRequestFilterComponent;

    @Autowired
    public JwtSecurityConfig(JwtRequestFilterComponent jwtRequestFilterComponent) {
        this.jwtRequestFilterComponent = jwtRequestFilterComponent;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            final AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    @Profile("prod")
    public SecurityFilterChain configure(final HttpSecurity http) throws Exception {
        return http.cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/auth/sign-up", "/auth/sign-in").permitAll()
                        .anyRequest().hasAuthority(ClientRolesEnum.ROLE_USER.name()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilterComponent, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    @Profile("!prod")
    public SecurityFilterChain devConfigure(final HttpSecurity http) throws Exception {
        return http.cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/auth/sign-up", "/auth/sign-in").permitAll()
                        .requestMatchers(SWAGGER_PATHS).permitAll()
                        .anyRequest().hasAuthority(ClientRolesEnum.ROLE_USER.name()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilterComponent, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}