package ir.sharifi.soroush.soroush_test_project.food.service;

import ir.sharifi.soroush.soroush_test_project.base.repository.BaseRepository;
import ir.sharifi.soroush.soroush_test_project.base.service.BaseServiceImpl;
import ir.sharifi.soroush.soroush_test_project.food.model.FoodStuff;
import ir.sharifi.soroush.soroush_test_project.food.repo.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodServiceImpl extends BaseServiceImpl<FoodStuff, Long> implements IFoodService {

    private final FoodRepository foodRepository;

    @Autowired
    public FoodServiceImpl(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    public BaseRepository<FoodStuff, Long> getRepository() {
        return foodRepository;
    }
}
