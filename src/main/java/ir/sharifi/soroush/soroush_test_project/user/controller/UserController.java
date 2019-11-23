package ir.sharifi.soroush.soroush_test_project.user.controller;

import ir.sharifi.soroush.soroush_test_project.base.controller.BaseController;
import ir.sharifi.soroush.soroush_test_project.base.service.BaseService;
import ir.sharifi.soroush.soroush_test_project.user.dto.*;
import ir.sharifi.soroush.soroush_test_project.user.model.AppUser;
import ir.sharifi.soroush.soroush_test_project.user.service.IUserService;
import ir.sharifi.soroush.soroush_test_project.utils.jwtUtils.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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



    @Autowired
    public UserController(IUserService userService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenProvider tokenProvider) {
        this.userService = userService;
    }

    @Override
    public BaseService<AppUser, Integer, UserInDto, UserOutDto, UserInsertDto, UserUpdateDto> getService() {
        return userService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity login(@Validated @RequestBody LoginRequest loginRequest) {

        return this.userService.login(loginRequest);


    }
    @PostMapping("/register")
    public ResponseEntity userRegister(@Validated @RequestBody UserRegisterDto userRegisterDto){
        return this.userService.register(userRegisterDto);

    }
}
