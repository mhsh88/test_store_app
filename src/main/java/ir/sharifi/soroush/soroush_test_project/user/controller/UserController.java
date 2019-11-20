package ir.sharifi.soroush.soroush_test_project.user.controller;

import ir.sharifi.soroush.soroush_test_project.base.controller.BaseController;
import ir.sharifi.soroush.soroush_test_project.base.service.BaseService;
import ir.sharifi.soroush.soroush_test_project.user.dto.*;
import ir.sharifi.soroush.soroush_test_project.user.model.AppUser;
import ir.sharifi.soroush.soroush_test_project.user.service.IUserService;
import ir.sharifi.soroush.soroush_test_project.utils.jwtUtils.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController<AppUser,Integer, UserInDto, UserOutDto, UserInsertDto, UserUpdateDto> {

    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final static String USERNAME_NOT_FOUND = "username.not.found";
    private final TokenProvider tokenProvider;

    @Autowired
    public UserController(IUserService userService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenProvider tokenProvider) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public BaseService<AppUser, Integer> getService() {
        return userService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity login(@Validated @RequestBody LoginRequest loginRequest) {
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
        AppUser user = userService.getUserByUserName(loginRequest.getUsername());




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
    @PostMapping("/register")
    public ResponseEntity userRegister(@Validated @RequestBody UserRegisterDto userRegisterDto){
        userRegisterDto.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        UserInsertDto map = modelMapper.map(userRegisterDto, UserInsertDto.class);
        return super.insert(map);
    }
}
