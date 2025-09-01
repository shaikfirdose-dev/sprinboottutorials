package com.firdose.springbootwebweek2.springbootwebweek2.config;

import com.firdose.springbootwebweek2.springbootwebweek2.filters.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityFilter {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/departments/**", "/auth/**").permitAll()
//                        .requestMatchers("/api/v1/departments/**").authenticated()
                        .anyRequest().authenticated())
                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(sessionConfig -> sessionConfig
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

//    @Bean
//    UserDetailsService myInMemoryUserDetailsService(){
//        UserDetails normalUser = User.withUsername("shaik")
//                .password(passwordEncoder().encode("shaik123"))
//                .roles("USER")
//                .build();
//
//        UserDetails adminUser = User.withUsername("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(normalUser, adminUser);
//    }


}
