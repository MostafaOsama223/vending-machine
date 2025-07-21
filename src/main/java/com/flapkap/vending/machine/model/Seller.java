package com.flapkap.vending.machine.model;

import jakarta.persistence.*;
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
@Table(name = "sellers")
public class Seller implements UserDetails {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private final transient Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_SELLER");
}
