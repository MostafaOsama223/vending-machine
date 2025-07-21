package com.flapkap.vending.machine.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Entity
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

    @Min(value = 0, message = "Deposit must be at least 0")
    private int deposit;

    private final transient Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_BUYER");
}
