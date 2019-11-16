package ir.sharifi.soroush.soroush_test_project.user.controller;

import ir.sharifi.soroush.soroush_test_project.base.controller.BaseController;
import ir.sharifi.soroush.soroush_test_project.base.service.BaseService;
import ir.sharifi.soroush.soroush_test_project.user.dto.UserInDto;
import ir.sharifi.soroush.soroush_test_project.user.dto.UserInsertDto;
import ir.sharifi.soroush.soroush_test_project.user.dto.UserOutDto;
import ir.sharifi.soroush.soroush_test_project.user.dto.UserUpdateDto;
import ir.sharifi.soroush.soroush_test_project.user.model.AppUser;
import ir.sharifi.soroush.soroush_test_project.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController<AppUser,Integer, UserInDto, UserOutDto, UserInsertDto, UserUpdateDto> {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public BaseService<AppUser, Integer> getService() {
        return userService;
    }
}
