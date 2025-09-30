package com.project.ecommerce.api_gateway.filters;


import com.project.ecommerce.api_gateway.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationGatewayFilter extends AbstractGatewayFilterFactory<AuthenticationGatewayFilter.Config> {

    private final JwtService jwtService;

    public AuthenticationGatewayFilter(JwtService jwtService){
        super(Config.class);
        this.jwtService = jwtService;
    }


    @Override
    public GatewayFilter apply(Config config) {

        if(!config.isEnabled){
            return (exchange, chain) -> chain.filter(exchange);
        }

        return (exchange, chain) -> {
            String authenticationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
            if (authenticationHeader == null || !authenticationHeader.startsWith("Bearer ")) {
                log.error("Missing or invalid Authorization header");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            String token = authenticationHeader.split("Bearer ")[1];

            exchange.getRequest()
                    .mutate()
                    .header("X-User-Id", jwtService.getUserIdFromToken(token).toString())
                    .build();


            return chain.filter(exchange);
        };
    }

    public static class Config {
        private boolean isEnabled;
    }
}
