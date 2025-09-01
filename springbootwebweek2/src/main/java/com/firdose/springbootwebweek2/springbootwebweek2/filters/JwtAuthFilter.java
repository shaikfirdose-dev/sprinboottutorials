package com.firdose.springbootwebweek2.springbootwebweek2.filters;

import com.firdose.springbootwebweek2.springbootwebweek2.entity.User;
import com.firdose.springbootwebweek2.springbootwebweek2.service.JwtService;
import com.firdose.springbootwebweek2.springbootwebweek2.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String requestTokenFroHeader = request.getHeader("Authorization");



        if (requestTokenFroHeader == null || !requestTokenFroHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = requestTokenFroHeader.split("Bearer ")[1];


        Long userId = jwtService.getUserIdFromToken(token);

        if(userId!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            User user = userService.getUserById(userId);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user, null, null);

            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);

    }
}
