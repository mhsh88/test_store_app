package ir.sharifi.soroush.soroush_test_project.food.controller;

import ir.sharifi.soroush.soroush_test_project.base.controller.BaseController;
import ir.sharifi.soroush.soroush_test_project.base.service.BaseService;
import ir.sharifi.soroush.soroush_test_project.food.dto.FoodInDto;
import ir.sharifi.soroush.soroush_test_project.food.dto.FoodInsertDto;
import ir.sharifi.soroush.soroush_test_project.food.dto.FoodOutDto;
import ir.sharifi.soroush.soroush_test_project.food.dto.FoodUpdateDto;
import ir.sharifi.soroush.soroush_test_project.food.model.FoodStuff;
import ir.sharifi.soroush.soroush_test_project.food.service.IFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/food")
public class FoodController extends BaseController<FoodStuff, Long, FoodInDto, FoodOutDto, FoodInsertDto, FoodUpdateDto> {

    private final IFoodService foodService;

    @Autowired
    public FoodController(IFoodService userService) {
        this.foodService = userService;
    }

    @Override
    public BaseService<FoodStuff, Long> getService() {
        return foodService;
    }
}
