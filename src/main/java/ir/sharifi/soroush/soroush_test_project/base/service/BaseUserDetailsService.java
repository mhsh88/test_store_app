package ir.sharifi.soroush.soroush_test_project.base.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public abstract class BaseUserDetailsService implements UserDetailsService {
    public abstract UserDetails loadUserByUsername(String username);
}
