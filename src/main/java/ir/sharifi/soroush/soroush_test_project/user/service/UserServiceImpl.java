package ir.sharifi.soroush.soroush_test_project.user.service;

import ir.sharifi.soroush.soroush_test_project.base.repository.BaseRepository;
import ir.sharifi.soroush.soroush_test_project.base.service.BaseServiceImpl;
import ir.sharifi.soroush.soroush_test_project.user.model.AppUser;
import ir.sharifi.soroush.soroush_test_project.user.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends BaseServiceImpl<AppUser, Integer> implements IUserService{

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public List<AppUser> getModels() {
        return super.getModels();
    }
}
