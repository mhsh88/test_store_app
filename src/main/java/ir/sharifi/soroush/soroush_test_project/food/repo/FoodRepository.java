package ir.sharifi.soroush.soroush_test_project.food.repo;

import ir.sharifi.soroush.soroush_test_project.base.repository.BaseRepository;
import ir.sharifi.soroush.soroush_test_project.food.model.FoodStuff;
import org.springframework.stereotype.Repository;


@Repository
public interface FoodRepository extends BaseRepository<FoodStuff, Long> {

}
