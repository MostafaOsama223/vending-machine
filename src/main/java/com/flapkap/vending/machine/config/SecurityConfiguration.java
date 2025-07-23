package com.flapkap.vending.machine.config;

import com.flapkap.vending.machine.service.impl.BuyerServiceImpl;
import com.flapkap.vending.machine.service.impl.SellerServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.RegexRequestMatcher.regexMatcher;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final BuyerServiceImpl buyerService;
    private final SellerServiceImpl sellerService;

    @Bean
    @Order(1)
    public SecurityFilterChain publicFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatchers(matchers -> matchers
                        .requestMatchers(HttpMethod.GET, "/api/v*/products/**")
                        .requestMatchers(HttpMethod.POST, "/api/v*/buyers/register")
                        .requestMatchers(HttpMethod.POST, "/api/v*/sellers/register")
                )
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.GET, "/api/v*/products/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v*/buyers/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v*/seller/register").permitAll()
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults()).httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain sellersFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatchers(matchers -> matchers
                        .requestMatchers(
                                regexMatcher(HttpMethod.POST, "/api/v\\d+/products"),
                                regexMatcher(HttpMethod.PUT, "/api/v\\d+/products/\\d+"),
                                regexMatcher(HttpMethod.DELETE, "/api/v\\d+/products/\\d+")
                        )
                )
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(
                                regexMatcher(HttpMethod.POST, "/api/v\\d+/products"),
                                regexMatcher(HttpMethod.PUT, "/api/v\\d+/products/\\d+"),
                                regexMatcher(HttpMethod.DELETE, "/api/v\\d+/products/\\d+")
                        ).hasRole("SELLER")
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults()).httpBasic(Customizer.withDefaults())
                .userDetailsService(sellerService)
                .build();
    }

    @Bean
    @Order(3)
    public SecurityFilterChain buyersFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/api/v*/buyers/**")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(
                                regexMatcher(HttpMethod.POST, "/api/v\\d+/buyers/deposit"),
                                regexMatcher(HttpMethod.POST, "/api/v\\d+/buyers/buy"),
                                regexMatcher(HttpMethod.POST, "/api/v\\d+/buyers/reset")
                        ).hasRole("BUYER")
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults()).httpBasic(Customizer.withDefaults())
                .userDetailsService(buyerService)
                .build();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_SELLER > ROLE_ANONYMOUS\n" + "ROLE_BUYER > ROLE_ANONYMOUS\n"
                + "ROLE_USER > ROLE_ANONYMOUS\n");

        return roleHierarchy;
    }

}
