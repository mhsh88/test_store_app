package ir.sharifi.soroush.soroush_test_project.config;

import ir.sharifi.soroush.soroush_test_project.base.service.BaseUserDetailsService;
import ir.sharifi.soroush.soroush_test_project.user.model.AppUser;
import ir.sharifi.soroush.soroush_test_project.user.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Component
@Service("securityService")
public class SoroushTestAppUserDetailsService extends BaseUserDetailsService {

    private final static String ERROR_LOGIN_BAD_CREDENTIALS = "error.login.bad.credentials";
    private final static String ERROR_USERNAME_NOT_FOUND = "error.username.not.found";

    private final UserRepository userRepository;

    @Autowired
    public SoroushTestAppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    @Cacheable("loadUserByUsername")
    public UserDetails loadUserByUsername(String username) {
        AppUser user;
        try {
            user = userRepository.findByUserName(username);
        }
        catch (Exception e) {
            throw new BadCredentialsException(ERROR_LOGIN_BAD_CREDENTIALS);
        }
        if (user == null) {
            throw new UsernameNotFoundException(ERROR_USERNAME_NOT_FOUND);
        }
        return org.springframework.security.core.userdetails.User.withUsername(username)
                .password(user.getPassword()).authorities(new ArrayList<>())
                .accountExpired(false).accountLocked(false).credentialsExpired(false)
                .disabled(false).build();
    }
}
