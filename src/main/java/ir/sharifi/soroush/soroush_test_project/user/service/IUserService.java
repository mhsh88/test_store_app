package ir.sharifi.soroush.soroush_test_project.user.service;

import ir.sharifi.soroush.soroush_test_project.base.service.BaseService;
import ir.sharifi.soroush.soroush_test_project.user.dto.*;
import ir.sharifi.soroush.soroush_test_project.user.model.AppUser;
import org.springframework.http.ResponseEntity;

public interface IUserService extends BaseService<AppUser, Integer, UserInDto, UserOutDto, UserInsertDto, UserUpdateDto> {
    AppUser getUserByUserName(String username);
    ResponseEntity<UserLoginResponse> login(LoginRequest loginRequest);

    ResponseEntity register(UserRegisterDto userRegisterDto);
}
