package com.flapkap.vending.machine.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    public UserDetails getByUsername(String username) throws IllegalStateException;

    public UserDetails getLoggedInUser() throws IllegalStateException;
}
