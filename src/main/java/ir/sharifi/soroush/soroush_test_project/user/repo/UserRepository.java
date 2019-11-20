package ir.sharifi.soroush.soroush_test_project.user.repo;

import ir.sharifi.soroush.soroush_test_project.base.repository.BaseRepository;
import ir.sharifi.soroush.soroush_test_project.user.model.AppUser;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends BaseRepository<AppUser, Integer> {

    AppUser findByUserName(String username);

}
