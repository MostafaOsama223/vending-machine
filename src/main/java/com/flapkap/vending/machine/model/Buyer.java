package com.flapkap.vending.machine.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "buyers")
public class Buyer implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Setter
    @Min(value = 0, message = "Deposit must be at least 0")
    private int deposit;

    private final transient Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_BUYER");
}
