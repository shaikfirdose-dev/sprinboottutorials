package com.project.ecommerce.api_gateway.filters;

import com.project.ecommerce.api_gateway.service.JwtService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class AuthorizationGatewayFilter extends AbstractGatewayFilterFactory<AuthorizationGatewayFilter.Config> {
    private final JwtService jwtService;

    public AuthorizationGatewayFilter(JwtService jwtService){
        super(Config.class);
        this.jwtService = jwtService;
    }


    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {
            String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                log.error("Missing or invalid Authorization header");
                exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String token = authorizationHeader.split("Bearer ")[1];
            List<String> userRoles = jwtService.getUserRoleFromToken(token);
            boolean hasRequiredRole = userRoles.stream().anyMatch(config.allowedRoles::contains);
            if (!hasRequiredRole) {
                log.error("User does not have the required roles: {}", config.allowedRoles);
                exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.FORBIDDEN);
                return exchange.getResponse().setComplete();
            }

            log.info("Authorization Filter: Incoming request: {}", exchange.getRequest().getURI());
            return chain.filter(exchange);
        };
    }

    @Data
    public static class Config {
        List<String> allowedRoles;
    }
}
