package ir.sharifi.soroush.soroush_test_project.user.service;

import ir.sharifi.soroush.soroush_test_project.base.repository.BaseRepository;
import ir.sharifi.soroush.soroush_test_project.base.service.BaseServiceImpl;
import ir.sharifi.soroush.soroush_test_project.user.dto.*;
import ir.sharifi.soroush.soroush_test_project.user.model.AppUser;
import ir.sharifi.soroush.soroush_test_project.user.repo.UserRepository;
import ir.sharifi.soroush.soroush_test_project.utils.jwtUtils.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends BaseServiceImpl<AppUser, Integer, UserInDto, UserOutDto, UserInsertDto, UserUpdateDto> implements IUserService{

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    private final static String USERNAME_NOT_FOUND = "username.not.found";
    private final TokenProvider tokenProvider;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public BaseRepository<AppUser, Integer> getRepository() {
        return userRepository;
    }

    @Override
    public AppUser getUserByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    @Cacheable("getAllUser")
    public List<UserOutDto> getModels() {
        return super.getModels();
    }

    @Override
    public ResponseEntity login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword());
        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(USERNAME_NOT_FOUND);
        }
        String token = tokenProvider.createToken(loginRequest.getUsername());
        AppUser user = getUserByUserName(loginRequest.getUsername());




        return ResponseEntity.ok(
                UserLoginResponse.UserLoginResponseBuilder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .userName(user.getUserName())
                        .password(user.getPassword())
                        .personnelNumber(user.getPersonnelNumber())
                        .token(token)
                        .build());

    }

    @Override
    public ResponseEntity register(UserRegisterDto userRegisterDto) {
        userRegisterDto.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        UserInsertDto map = modelMapper.map(userRegisterDto, UserInsertDto.class);

        return ResponseEntity.ok(super.insert(map));
    }
}
