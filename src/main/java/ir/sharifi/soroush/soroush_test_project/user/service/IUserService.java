package ir.sharifi.soroush.soroush_test_project.user.service;

import ir.sharifi.soroush.soroush_test_project.base.service.BaseService;
import ir.sharifi.soroush.soroush_test_project.user.model.AppUser;

public interface IUserService extends BaseService<AppUser, Integer> {
    AppUser getUserByUserName(String username);
}
