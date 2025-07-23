package com.flapkap.vending.machine.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    public UserDetails getByUsername(String username) throws IllegalStateException;

    public UserDetails getLoggedInUser() throws IllegalStateException;
}
