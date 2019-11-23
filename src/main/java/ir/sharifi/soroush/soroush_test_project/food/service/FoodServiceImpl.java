package ir.sharifi.soroush.soroush_test_project.food.service;

import ir.sharifi.soroush.soroush_test_project.base.repository.BaseRepository;
import ir.sharifi.soroush.soroush_test_project.base.service.BaseServiceImpl;
import ir.sharifi.soroush.soroush_test_project.food.dto.FoodInDto;
import ir.sharifi.soroush.soroush_test_project.food.dto.FoodInsertDto;
import ir.sharifi.soroush.soroush_test_project.food.dto.FoodOutDto;
import ir.sharifi.soroush.soroush_test_project.food.dto.FoodUpdateDto;
import ir.sharifi.soroush.soroush_test_project.food.model.FoodStuff;
import ir.sharifi.soroush.soroush_test_project.food.repo.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodServiceImpl extends BaseServiceImpl<FoodStuff, Long, FoodInDto, FoodOutDto, FoodInsertDto, FoodUpdateDto> implements IFoodService {

    private final FoodRepository foodRepository;

    @Autowired
    public FoodServiceImpl(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    public BaseRepository<FoodStuff, Long> getRepository() {
        return foodRepository;
    }

    @Override
    @Cacheable("getAllFood")
    public List<FoodOutDto> getModels() {
        return super.getModels();
    }
}
