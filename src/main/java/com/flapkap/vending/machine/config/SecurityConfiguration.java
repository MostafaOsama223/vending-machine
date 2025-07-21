package com.flapkap.vending.machine.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers.pathMatchers;

@Slf4j
@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .matchers(
                                pathMatchers(HttpMethod.GET, "/api/v*/public/**"),
                                pathMatchers(HttpMethod.POST, "/api/v*/buyer"),
                                pathMatchers(HttpMethod.POST, "/api/v*/seller")
                        ).permitAll()

                        .matchers(
                                pathMatchers(HttpMethod.POST, "/api/v*/products"),
                                pathMatchers(HttpMethod.PUT, "/api/v*/products/.*"),
                                pathMatchers(HttpMethod.DELETE, "/api/v*/products/.*")
                        ).hasRole("SELLER")

                        .matchers(
                                pathMatchers(HttpMethod.POST, "/api/v*/deposit"),
                                pathMatchers(HttpMethod.POST, "/api/v*/buy"),
                                pathMatchers(HttpMethod.POST, "/api/v*/reset")
                        ).hasRole("BUYER")

                        .anyExchange().authenticated())
                .formLogin(Customizer.withDefaults()).httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails sellerUser = User.withDefaultPasswordEncoder()
                .username("seller")
                .password("password")
                .roles("SELLER", "USER")
                .build();

        UserDetails buyerUser = User.withDefaultPasswordEncoder()
                .username("buyer")
                .password("password")
                .roles("BUYER", "USER")
                .build();

        return new MapReactiveUserDetailsService(sellerUser, buyerUser);
    }

}